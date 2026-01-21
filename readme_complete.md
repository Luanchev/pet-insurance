# 🐾 Pet Insurance - Sistema de Gestión de Pólizas para Mascotas

Sistema backend reactivo para la cotización y emisión de pólizas de seguro para mascotas, implementado con **Clean Architecture** y **Arquitectura Hexagonal**.


---

## Tabla de Contenidos

- [Descripción](#-descripción)
- [Arquitectura](#-arquitectura)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [API Endpoints](#-api-endpoints)
- [Reglas de Negocio](#-reglas-de-negocio)

---

## Descripción

**AseguraTuPata** es una plataforma de seguros para mascotas que permite a los clientes:

1. **Cotizar** un seguro ingresando los datos de su mascota
2. **Emitir pólizas** una vez que aceptan la cotización
3. **Recibir notificaciones** automáticas para el sistema de facturación

---

## Arquitectura

Este proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)** con **Clean Architecture**.

---

## Características

### Funcionales
- Cotización de seguros con cálculo de precios dinámico
- Validación de reglas de negocio (edad, especie, plan)
- Emisión de pólizas desde cotizaciones válidas
- Publicación de eventos de dominio (PolicyIssuedEvent)
- Manejo de errores con respuestas HTTP descriptivas

---

## Tecnologías

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.2.1** - Framework base
- **Spring WebFlux** - API REST reactiva (non-blocking I/O)
- **Spring Data R2DBC** - Acceso reactivo a base de datos
- **PostgreSQL 15** - Base de datos relacional

### Build & Deploy
- **Maven** - Gestión de dependencias
- **Docker & Docker Compose** - Contenedorización

---

## Requisitos Previos

Asegúrate de tener instalado:

- **Java 21** 
- **Maven 3.8+** 
- **Docker & Docker Compose** 
- **Git** 

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Luanchev/pet-insurance/
cd pet-insurance
```

### 2. Levantar PostgreSQL con Docker

```bash
docker-compose up -d
```

Esto levantará:
- **PostgreSQL** en `localhost:5432`
- Base de datos: `pet_insurance`
- Usuario: `postgres`
- Contraseña: `250819`

### 3. Verificar que PostgreSQL esté corriendo

```bash
docker ps
```

Deberías ver:
```
CONTAINER ID   IMAGE            STATUS
abc123         postgres:15      Up 10 seconds
```

### 4. Compilar el proyecto

```bash
mvn clean compile
```

### 5. Ejecutar la aplicación

La aplicación arrancará en: **http://localhost:8080**

### 6. Verificar que arrancó correctamente

---

## Uso

### Flujo de Negocio

```
1. Cliente solicita cotización
   
2. Sistema calcula precio según reglas
   
3. Cliente recibe cotización (válida por 30 días)
   
4. Cliente emite póliza desde la cotización
   
5. Sistema genera póliza activa
   
6. Sistema publica evento para facturación
```

### Ejemplo Práctico

#### Paso 1: Crear Cotización

**Request:**
```bash
curl -X POST http://localhost:8080/api/quotes \
  -H "Content-Type: application/json" \
  -d '{
    "petName": "Max",
    "species": "DOG",
    "breed": "Labrador",
    "ageInYears": 4,
    "plan": "BASIC"
  }'
```

**Response (201 Created):**
```json
{
  "quoteId": "550e8400-e29b-41d4-a716-446655440000",
  "monthlyPrice": 12.00,
  "expiresAt": "2026-02-19T15:30:00"
}
```

#### Paso 2: Emitir Póliza

**Request:**
```bash
curl -X POST http://localhost:8080/api/policies \
  -H "Content-Type: application/json" \
  -d '{
    "quoteId": "550e8400-e29b-41d4-a716-446655440000",
    "ownerName": "Juan Pérez",
    "ownerId": "12345678",
    "ownerEmail": "juan@example.com"
  }'
```

**Response (201 Created):**
```json
{
  "policyId": "660e8400-e29b-41d4-a716-446655440001",
  "quoteId": "550e8400-e29b-41d4-a716-446655440000",
  "ownerName": "Juan Pérez",
  "monthlyPrice": 12.00,
  "issuedAt": "2026-01-20T15:35:00"
}
```

**Logs del Evento:**
```
========================================
EVENTO DE DOMINIO: PolicyIssuedEvent
========================================
Policy ID: 660e8400-e29b-41d4-a716-446655440001
Owner: Juan Pérez (juan@example.com)
Pet: Max
Monthly Price: $12.00
========================================
Este evento sería consumido por el sistema de facturación
========================================
```

---

## API Endpoints

### Cotizaciones

#### `POST /api/quotes` - Crear Cotización

**Request Body:**
```json
{
  "petName": "string",        // Nombre de la mascota (requerido)
  "species": "DOG" | "CAT",   // Especie (requerido)
  "breed": "string",          // Raza (requerido)
  "ageInYears": number,       // Edad en años (1-10, requerido)
  "plan": "BASIC" | "PREMIUM" // Plan (requerido)
}
```

**Response (201 Created):**
```json
{
  "quoteId": "uuid",
  "monthlyPrice": number,
  "expiresAt": "ISO-8601 datetime"
}
```

**Errores Posibles:**
- `400 Bad Request` - Mascota > 10 años, edad inválida, campos vacíos
- `500 Internal Server Error` - Error del servidor

---

### Pólizas

#### `POST /api/policies` - Emitir Póliza

**Request Body:**
```json
{
  "quoteId": "uuid",          // ID de cotización (requerido)
  "ownerName": "string",      // Nombre del dueño (requerido)
  "ownerId": "string",        // Identificación (requerido)
  "ownerEmail": "email"       // Email válido (requerido)
}
```

**Response (201 Created):**
```json
{
  "policyId": "uuid",
  "quoteId": "uuid",
  "ownerName": "string",
  "monthlyPrice": number,
  "issuedAt": "ISO-8601 datetime"
}
```

**Errores Posibles:**
- `404 Not Found` - Cotización no existe
- `400 Bad Request` - Cotización expirada, email inválido
- `500 Internal Server Error` - Error del servidor

---

## Reglas de Negocio

### Cálculo de Precio Mensual

El precio se calcula siguiendo esta fórmula:

```
Precio Base = $10 USD

1. Aplicar multiplicador por especie:
   • Perro: +20% (×1.20)
   • Gato: +10% (×1.10)

2. Aplicar recargo por edad senior (> 5 años):
   • +50% (×1.50)

3. Aplicar multiplicador del plan:
   • BASIC: sin cambio (×1)
   • PREMIUM: duplica (×2)
```

### Restricciones

- **Edad permitida:** 1 - 10 años
- **Especies:** Solo Perro (DOG) o Gato (CAT)
- **Vigencia de cotización:** 30 días
- **Estados de póliza:** ACTIVE, CANCELLED, EXPIRED

---

## Autor
**Luis Angel Echeverry**
---

*Última actualización: Enero 2026*
