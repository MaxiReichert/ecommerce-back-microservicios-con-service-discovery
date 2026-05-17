package com.tienda.ecommerce.producto.dto;

import com.tienda.ecommerce.producto.dominio.Producto;

public class ProductoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoria;
    private String emoji;
    private String badge;

    // ─── Mapping desde entidad ────────────────────────────────────────────────

    public static ProductoDTO desdeEntidad(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.id          = producto.getId();
        dto.nombre      = producto.getNombre();
        dto.descripcion = producto.getDescripcion();
        dto.precio      = producto.getPrecio();
        dto.categoria   = producto.getCategoria();
        dto.emoji       = producto.getEmoji();
        dto.badge       = producto.getBadge();
        return dto;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public Long getId()          { return id; }
    public String getNombre()    { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Double getPrecio()    { return precio; }
    public String getCategoria() { return categoria; }
    public String getEmoji()     { return emoji; }
    public String getBadge()     { return badge; }
}
