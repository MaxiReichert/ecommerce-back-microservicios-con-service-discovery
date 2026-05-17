package com.tienda.ecommerce.pedido.servicio;

import com.tienda.ecommerce.pedido.dominio.ItemPedido;
import com.tienda.ecommerce.pedido.dominio.Pedido;
import com.tienda.ecommerce.pedido.dto.ItemPedidoDTO;
import com.tienda.ecommerce.pedido.dto.PedidoRequestDTO;
import com.tienda.ecommerce.pedido.repositorio.PedidoRepositorio;
import com.tienda.ecommerce.pedido.dto.externo.ProductoDTO;
import com.tienda.ecommerce.pedido.dto.externo.PagoResultado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PedidoServicioImpl implements PedidoServicio {

    private final PedidoRepositorio pedidoRepositorio;
    private final RestTemplate restTemplate;

    public PedidoServicioImpl(PedidoRepositorio pedidoRepositorio, RestTemplate restTemplate) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.restTemplate = restTemplate;
    }

    @Override
    public String crearPedido(PedidoRequestDTO request) {
        Pedido pedido = new Pedido();
        pedido.setNombreCliente(request.getNombre());
        pedido.setTelefono(request.getTelefono());
        pedido.setDireccion(request.getDireccion());
        pedido.setColonia(request.getColonia());
        pedido.setReferencias(request.getReferencias());
        pedido.setMetodoPago(request.getMetodoPago());
        pedido.setFechaCreacion(LocalDateTime.now());

        // Generar código de seguimiento
        String codigo = "ORD-2026-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        pedido.setCodigoSeguimiento(codigo);

        double subtotal = 0.0;

        for (ItemPedidoDTO itemDto : request.getItems()) {
            // Consultamos el precio real al microservicio de productos
            ProductoDTO producto = restTemplate
                    .getForObject("http://localhost:8081/api/productos/" + itemDto.getProductoId(), ProductoDTO.class);

            if (producto == null)
                throw new RuntimeException("Producto no encontrado");

            ItemPedido item = new ItemPedido();
            item.setProductoId(producto.getId());
            item.setCantidad(itemDto.getCantidad());
            item.setPrecioUnitario(producto.getPrecio());

            subtotal += (producto.getPrecio() * itemDto.getCantidad());

            pedido.agregarItem(item);
        }

        // Validar descuento con el microservicio de pago
        Double porcentajeDescuento = 0.0;
        if (request.getCodigoDescuento() != null && !request.getCodigoDescuento().isEmpty()) {
            try {
                porcentajeDescuento = restTemplate.getForObject(
                        "http://localhost:8082/api/descuentos/validar?codigo=" + request.getCodigoDescuento(),
                        Double.class);
            } catch (Exception e) {
                porcentajeDescuento = 0.0;
            }
        }
        if (porcentajeDescuento == null)
            porcentajeDescuento = 0.0;

        double descuento = subtotal * porcentajeDescuento;

        double envio = 45.0; // Costo fijo de envío como en el frontend
        double total = subtotal - descuento + envio;

        // Procesar pago simulado llamando al microservicio de pago
        PagoResultado resultadoPago = restTemplate.postForObject(
                "http://localhost:8082/api/pagos/procesar?metodoPago=" + request.getMetodoPago() + "&total=" + total,
                null,
                PagoResultado.class);

        if (resultadoPago != null) {
            pedido.setEstadoPago(resultadoPago.getEstadoPago());
            pedido.setIdTransaccion(resultadoPago.getIdTransaccion());
        } else {
            pedido.setEstadoPago("PENDIENTE");
        }

        pedido.setSubtotal(subtotal);
        pedido.setDescuento(descuento);
        pedido.setEnvio(envio);
        pedido.setTotal(total);

        pedidoRepositorio.save(pedido);

        return codigo;
    }
}
