### Capa de DOMINIO

```plantuml
@startuml
namespace Capa_Logica_Dominio {
    class Configuracion {
        - idConfiguracion: String
        - impuestoIVA: double
        - rangos: List<Rango>
        - porcentajeSeguro: double
        - tarifaEntregaInmediata: double
        - tarifaEntregaSegundoDia: double
        - tarifaEntregaNormal: double
    }

    interface ConfiguracionService {
        + obtenerConfiguracion(): ConfiguracionDTO
        + guardarConfiguracion(configuracion: ConfiguracionDTO): void
    }

    class ConfiguracionServiceImpl {
        - configDAO: DAO<Configuracion>
        + ConfiguracionServiceImpl(configDAO: DAO<Configuracion>)
        + obtenerConfiguracion(): ConfiguracionDTO
        + guardarConfiguracion(configuracion: ConfiguracionDTO): void
    }

    ConfiguracionService <|.. ConfiguracionServiceImpl
    ConfiguracionServiceImpl --> Configuracion
    Configuracion o-- Rango

    interface ClienteService {
        + crearCliente(cliente: ClienteDTO, contrasena: String): void
        + modificarCliente(cliente: ClienteDTO): void
        + archivarCliente(cliente: ClienteDTO): void
        + buscarClientePorID(id: String): ClienteDTO
        + buscarClientePorNombre(nombre: String): ClienteDTO
        + mostrarListaClientes(): List<ClienteDTO>
        + obtenerListadoPaquetesPorIdCliente(id: String, configuracion: ConfiguracionDTO): ClienteEnviosDTO
        + validarLogin(cliente: ClienteDTO, contrasena: String): ClienteDTO
    }

    interface PaqueteService {
        + crearPaquete(paquete: PaqueteDTO, configuracion: ConfiguracionDTO): void
        + buscarPaquetePorID(id: String): PaqueteDTO
        + mostrarListaPaquetes(): List<PaqueteDTO>
        + mostrarPaquetesSinEnvio(): List<PaqueteDTO>
        + registrarMovimientoPaquete(idPaquete: String, nombreOficina: String, fechaHora: LocalDateTime, esLlegada: boolean, textoFormateado: String): void
        + agregarPuntoRuta(idPaquete: String, nombreOficina: String): void
        + obtenerPaquetesPorDestinatario(idDestinatario: String): List<PaqueteDTO>
        + obtenerPaquetesPorRemitente(idRemitente: String): List<PaqueteDTO>
        + obtenerTextosRutaConEstados(idPaquete: String): List<String>
    }

    interface EnvioService {
        + realizarEnvio(envio: EnvioDTO, configuracion: ConfiguracionDTO): void
        + buscarEnvioPorID(id: String): EnvioDTO
        + obtenerCostoTotalEnvio(idEnvio: String): double
        + mostrarListaEnvios(configuracion: ConfiguracionDTO): List<EnvioDTO>
        + cancelarEnvio(envio: EnvioDTO): void
    }

    interface OficinaService {
        + crearOficina(oficina: OficinaDTO): void
        + modificarOficina(oficina: OficinaDTO): void
        + archivarOficina(oficina: OficinaDTO): void
        + buscarOficinaPorID(id: String): OficinaDTO
        + buscarOficinaPorNombre(nombre: String): OficinaDTO
        + mostrarListaOficinas(): List<OficinaDTO>
    }

    class OficinaServiceImpl {
        - oficinaDAO: DAO<Oficina>
        + crearOficina(oficina: OficinaDTO): void
        + modificarOficina(oficina: OficinaDTO): void
        + archivarOficina(oficina: OficinaDTO): void
        + buscarOficinaPorID(id: String): OficinaDTO
        + buscarOficinaPorNombre(nombre: String): OficinaDTO
        + mostrarListaOficinas(): List<OficinaDTO>
    }

    class ClienteServiceImpl {
        - clienteDAO: DAO<Cliente>
        + crearCliente(cliente: ClienteDTO, contrasena: String): void
        + modificarCliente(cliente: ClienteDTO): void
        + archivarCliente(cliente: ClienteDTO): void
        + buscarClientePorID(id: String): ClienteDTO
        + buscarClientePorNombre(nombre: String): ClienteDTO
        + mostrarListaClientes(): List<ClienteDTO>
        + obtenerListadoPaquetesPorIdCliente(id: String, configuracion: ConfiguracionDTO): ClienteEnviosDTO
        + validarLogin(cliente: ClienteDTO, contrasena: String): ClienteDTO
    }

    class PaqueteServiceImpl {
        - paqueteDAO: DAO<Paquete>
        - envioDAO: DAO<Envio>
        - oficinaDAO: DAO<Oficina>
        + crearPaquete(paquete: PaqueteDTO, configuracion: ConfiguracionDTO): void
        + buscarPaquetePorID(id: String): PaqueteDTO
        + mostrarListaPaquetes(): List<PaqueteDTO>
        + mostrarPaquetesSinEnvio(): List<PaqueteDTO>
        + registrarMovimientoPaquete(idPaquete: String, nombreOficina: String, fechaHora: LocalDateTime, esLlegada: boolean, textoFormateado: String): void
        + agregarPuntoRuta(idPaquete: String, nombreOficina: String): void
        + obtenerPaquetesPorDestinatario(idDestinatario: String): List<PaqueteDTO>
        + obtenerPaquetesPorRemitente(idRemitente: String): List<PaqueteDTO>
        + obtenerTextosRutaConEstados(idPaquete: String): List<String>
    }

    class EnvioServiceImpl {
        - envioDAO: DAO<Envio>
        - clienteDAO: DAO<Cliente>
        - configDAO: DAO<Configuracion>
        - paqueteDAO: DAO<Paquete>
        + realizarEnvio(envio: EnvioDTO, configuracion: ConfiguracionDTO): void
        + buscarEnvioPorID(id: String): EnvioDTO
        + obtenerCostoTotalEnvio(idEnvio: String): double
        + mostrarListaEnvios(configuracion: ConfiguracionDTO): List<EnvioDTO>
        + cancelarEnvio(envio: EnvioDTO): void
    }

    class Cliente {
        - idCliente: String
        - nombre: String
        - direccion: String
        - telefono: String
        - listaEnvios: List<Envio>
        - isActive: boolean
        - contrasena: String
    }

    class Envio {
        - idEnvio: String
        - idRemitente: String
        - idDestinatario: String
        - listaPaquetes: List<Paquete>
        - listaIdPaquetes: List<String>
        - rapidez: TipoServicio
        - metodoPago: MetodoPago
        - costoTotal: double
        - estadoEnvio: EstadoEnvio
        + calcularCostoTotal(tarifaInmediata: double, tarifaSegundoDia: double, tarifaNormal: double, IVA: double): void
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
        - ruta: RutaSeguimiento
        + {abstract} calcularCostoBase(rangos: List<Rango>): double
        - calcularCostoSeguro(): double
    }

    class Sobre {
        - tamano: Tamano
        + calcularCostoBase(rangos: List<Rango>): double
    }

    class Caja {
        - alto: double
        - ancho: double
        - largo: double
        + calcularCostoBase(rangos: List<Rango>): double
    }

    class RutaSeguimiento {
        - puntosIntermedios: List<PuntoIntermedio>
        + agregarPaso(puntoIntermedio: PuntoIntermedio): void
    }

    class Oficina {
        - idOficina: String
        - nombre: String
        - direccion: String
        - telefono: String
        - active: boolean
    }

    class PuntoIntermedio {
        - horaLlegada: LocalDateTime
        - horaSalida: LocalDateTime
        - oficina: Oficina
        - llegadaTexto: String
        - salidaTexto: String
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

    enum EstadoEnvio {
        ACTIVO
        CANCELADO
    }

    Paquete -- Rango
    Cliente "1" o-- "0..*" Envio
    Envio *-- Paquete 
    Paquete o-- RutaSeguimiento 
    Envio ..> TipoServicio
    Envio ..> MetodoPago
    Envio ..> EstadoEnvio
    Sobre ..> Tamano
    Paquete <|-- Sobre 
    Paquete <|-- Caja 
    PuntoIntermedio o-- Oficina
    RutaSeguimiento *-- PuntoIntermedio
    ClienteService <|.. ClienteServiceImpl
    EnvioService <|.. EnvioServiceImpl
    PaqueteService <|.. PaqueteServiceImpl
    OficinaService <|.. OficinaServiceImpl
}
@enduml
```

### Capa de ACCESO A DATOS

```plantuml
@startuml
namespace Capa_Acceso_Datos {



    interface DAO<T> {
        + guardar(entidad: T): void
        + obtenerTodos(): List<T>
        + buscarPorId(id: String): Optional<T>
        + eliminar(id: String): void
    }

    abstract class DAOXML<T> {
        - rutaArchivo: String
        + guardar(entidad: T): void
        + obtenerTodos(): List<T>
        + buscarPorId(id: String): Optional<T>
        + eliminar(id: String): void
    }

    abstract class DAOBIN<T> {
        - rutaArchivo: String
        + guardar(entidad: T): void
        + obtenerTodos(): List<T>
        + buscarPorId(id: String): Optional<T>
        + eliminar(id: String): void
    }

    DAO <|.. DAOXML
    DAO <|.. DAOBIN

    class ClienteXmlDAO {
        + ClienteXmlDAO()
        + buscarPorNombre(nombre: String): List<Cliente>
        + guardar(entidad: Cliente): void
        + obtenerTodos(): List<Cliente>
        + buscarPorId(id: String): Optional<Cliente>
        + eliminar(id: String): void
    }
    class ClienteBinDAO {
        + ClienteBinDAO()
        + buscarPorNombre(nombre: String): List<Cliente>
        + guardar(entidad: Cliente): void
        + obtenerTodos(): List<Cliente>
        + buscarPorId(id: String): Optional<Cliente>
        + eliminar(id: String): void
    }

    class OficinaXmlDAO {
        + OficinaXmlDAO()
        + buscarPorNombre(nombre: String): Optional<Oficina>
        + guardar(entidad: Oficina): void
        + obtenerTodos(): List<Oficina>
        + buscarPorId(id: String): Optional<Oficina>
        + eliminar(id: String): void
    }
    class OficinaBinDAO {
        + OficinaBinDAO()
        + buscarPorNombre(nombre: String): Optional<Oficina>
        + guardar(entidad: Oficina): void
        + obtenerTodos(): List<Oficina>
        + buscarPorId(id: String): Optional<Oficina>
        + eliminar(id: String): void
    }

    class EnvioXmlDAO {
        + EnvioXmlDAO()
        + guardar(entidad: Envio): void
        + obtenerTodos(): List<Envio>
        + buscarPorId(id: String): Optional<Envio>
        + eliminar(id: String): void
    }
    class EnvioBinDAO {
        + EnvioBinDAO()
        + guardar(entidad: Envio): void
        + obtenerTodos(): List<Envio>
        + buscarPorId(id: String): Optional<Envio>
        + eliminar(id: String): void
    }

    class PaqueteXmlDAO {
        + PaqueteXmlDAO()
        + guardar(entidad: Paquete): void
        + obtenerTodos(): List<Paquete>
        + buscarPorId(id: String): Optional<Paquete>
        + eliminar(id: String): void
    }
    class PaqueteBinDAO {
        + PaqueteBinDAO()
        + guardar(entidad: Paquete): void
        + obtenerTodos(): List<Paquete>
        + buscarPorId(id: String): Optional<Paquete>
        + eliminar(id: String): void
    }
class ConfiguracionXmlDAO {
        + ConfiguracionXmlDAO ()
        + guardar(entidad: Configuracion): void
        + obtenerTodos(): List<Configuracion>
        + buscarPorId(id: String): Optional<Configuracion>
        + eliminar(id: String): void
    }
    class ConfiguracionBinDAO {
        + ConfiguracionBinDAO()
        + guardar(entidad: Configuracion): void
        + obtenerTodos(): List<Configuracion>
        + buscarPorId(id: String): Optional<Configuracion>
        + eliminar(id: String): void
    }

    DAOXML <|-- ClienteXmlDAO : <Cliente>
    DAOXML <|-- EnvioXmlDAO : <Envio>
    DAOXML <|-- PaqueteXmlDAO : <Paquete>
    DAOXML <|-- OficinaXmlDAO : <Oficina>
DAOXML <|-- ConfiguracionXmlDAO : <Configuracion>

    DAOBIN <|-- ClienteBinDAO : <Cliente>
    DAOBIN <|-- EnvioBinDAO : <Envio>
    DAOBIN <|-- PaqueteBinDAO : <Paquete>
    DAOBIN <|-- OficinaBinDAO : <Oficina>
    DAOBIN <|-- ConfiguracionBinDAO : <Configuracion>
}
@enduml
```
