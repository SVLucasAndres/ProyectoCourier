### POJOs

```plantuml
@startuml
namespace POJO {
    class Cliente {
        - idCliente: String
        - nombre: String
        - direccion: String
        - telefono: String
        - listaEnvios: List<Envio>
    }

    class Envio {
        - idEnvio: String
        - remitente: Cliente
        - destinatario: Cliente
        - listaPaquetes: List<Paquete>
        - rapidez: TipoRapidez
        - metodoPago: MetodoPago
        - ruta: RutaSeguimiento
        + calcularCostoTotal(): double
    }

    class Rango {
        - nombre: String
        - pesoMinimo: double
        - pesoMaximo: double
        - costoPorKilogramo: double
    }

    abstract class Paquete {
        - idPaquete: String
        - peso: double
        - valorContenido: double
        - tieneSeguro: boolean
        - porcentajeSeguro: double
        + {abstract} calcularCostoBase(List<Rango> rangos): double
        - calcularCostoSeguro(): double
    }

    class Sobre {
        - tamano: Tamano
        + calcularCostoBase(): double
    }

    class Caja {
        - alto: double
        - ancho: double
        - largo: double
        + calcularCostoBase(): double
    }

    class RutaSeguimiento {
        - registros: List<RegistroOficina>
        + agregarPaso(RegistroOficina)
    }

    class Oficina {
        - idOficina: String
        - nombre: String
        - direccion: String
        - telefono: String
    }

    class PuntoIntermedio {
        - nombreOficina: String
        - horaLlegada: LocalDateTime
        - horaSalida: LocalDateTime
    }

    enum TipoServicio {
        ENTREGA_SIGUIENTE_DIA
        SEGUNDO_DIA
        NORMAL
    }

    enum MetodoPago {
        EFECTIVO
        TARJETA_CREDITO_DEBITO
        PAYPAL
    }

    enum Tamano {
        XS
        PEQUENO
        MEDIANO
        GRANDE
        XL
    }

    Paquete -- Rango
    Cliente "1" o-- "0..*" Envio
    Envio *-- Paquete 
    Paquete o-- RutaSeguimiento 
    Envio ..> TipoServicio
    Envio ..> MetodoPago 
    Sobre ..> Tamano
    Paquete <|-- Sobre 
    Paquete <|-- Caja 
    PuntoIntermedio o-- Oficina
    RutaSeguimiento *-- PuntoIntermedio
}
@enduml
