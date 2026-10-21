# SmartBudget Android — Laboratorio Servicios REST (Retrofit)

Front-end Android para consumo de servicios REST del proyecto **SmartBudget**
(plataforma que agrega, compara y optimiza compras en el e-commerce colombiano
bajo un presupuesto estricto — Universidad Piloto de Colombia).

## 1. Objetivo del laboratorio
Crear un front Android que consuma servicios REST usando **Retrofit**, con:
- Una capa de red (`data/remote`) separada de la UI.
- Servicios **GET** y **POST** relacionados con el proyecto del curso.

## 2. Stack
| Capa          | Tecnología                            |
|---------------|---------------------------------------|
| Lenguaje      | Kotlin 1.9                            |
| UI            | Android SDK 34, ViewBinding, Material |
| HTTP          | Retrofit 2.9 + OkHttp 4.12            |
| Serialización | Gson                                  |
| Asincronía    | Kotlin Coroutines + ViewModel         |

## 3. Estructura
```
app/src/main/java/co/edu/unipiloto/smartbudget/
├── data/
│   ├── model/          Producto, Tienda, OptimizacionRequest/Response, BusquedaFavoritaRequest/Response
│   ├── remote/         ApiService (Retrofit), RetrofitClient (singleton OkHttp + Gson)
│   └── repository/     SmartBudgetRepository (envuelve respuestas en Result)
└── ui/
    ├── MainActivity.kt         Buscar + Favorito + Optimizar
    ├── OptimizarActivity.kt    Combo óptimo bajo presupuesto
    ├── adapter/                ProductoAdapter (RecyclerView)
    └── viewmodel/              ProductosViewModel, OptimizarViewModel
```

## 4. Endpoints consumidos
Base URL: `RetrofitClient.BASE_URL` (editar en `data/remote/RetrofitClient.kt`).

### GET
| Método | Ruta             | Query / Path                  | Devuelve                 |
|--------|------------------|-------------------------------|--------------------------|
| GET    | `/tiendas`       | —                             | `List<Tienda>`           |
| GET    | `/productos`     | `q`, `tienda?`, `maxPrecio?`  | `List<Producto>`         |
| GET    | `/productos/{id}`| `id`                          | `Producto`               |

### POST
| Método | Ruta           | Body                      | Devuelve                 |
|--------|----------------|---------------------------|--------------------------|
| POST   | `/optimizar`   | `OptimizacionRequest`     | `OptimizacionResponse`   |
| POST   | `/favoritos`   | `BusquedaFavoritaRequest` | `BusquedaFavoritaResponse` |

### Ejemplo de body — `POST /optimizar`
```json
{
  "consulta": "audífonos bluetooth",
  "presupuestoCop": 150000,
  "tiendas": ["Falabella", "Alkosto", "Éxito"],
  "incluirAgotados": false
}
```

## 5. Permisos
`AndroidManifest.xml` declara `INTERNET` y `ACCESS_NETWORK_STATE`.
Durante desarrollo con backend HTTP local se usa `android:usesCleartextTraffic="true"`;
quitar antes de publicar.

## 6. Cómo correrlo
1. Abrir el proyecto en **Android Studio** (Giraffe o superior).
2. Esperar `Gradle sync`.
3. Editar `BASE_URL` en `RetrofitClient.kt` apuntando al backend real.
   - Emulador contra servidor local: `http://10.0.2.2:<puerto>/`
4. **Run** sobre emulador o dispositivo (minSdk 24).

## 7. Flujo de uso
1. Escribir una consulta (ej. `audífonos bluetooth`) y presupuesto en COP.
2. **Buscar** → `GET /productos` → lista los resultados agregados.
3. **Favorito** → `POST /favoritos` → guarda la búsqueda para seguimiento.
4. **Optimizar** → abre `OptimizarActivity` que hace `POST /optimizar`
   y muestra el combo óptimo bajo el presupuesto.

## 8. Logs de red
`HttpLoggingInterceptor` nivel `BODY` imprime request/response en Logcat
(filtro: `OkHttp`). Reducir a `BASIC`/`NONE` para release.

## 9. Entrega
Repositorio: https://github.com/Diegoab02 — subir y entregar la URL en Moodle.
