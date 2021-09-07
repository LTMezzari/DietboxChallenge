# Desafio Dietbox

Com objetivo de visualizar meu estilo de trabalho, bem como a compatibilidade com o estilo de trabalho da Dietbox, foi proposto o desafio técnico abaixo:

## Objetivos

1. Crie um app para Android utilizando a api The Movie DB (https://www.themoviedb.org) para mostrar uma lista de filmes trending.
2. O app deve funcionar offline mostrando os últimos resultados pesquisados (caso existam) e mostrando as mensagens de erro/aviso apropriadas.
3. Deve ser possível pesquisar filmes pelo nome.
4. Em nossos projetos atuais utilizando MVVM, LiveData, Room, Retrofit e Dagger 2. Porém, a escolha de tecnologias e padrões é livre.
5. Suba seu projeto em um sua conta do github como projeto público e nos envie.

## Tecnologias Usadas

- Java 8;
- Model View ViewModel (MVVM);
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started);
- [Dagger](https://dagger.dev/);
- [Retrofit](https://square.github.io/retrofit/);
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata);
- [Room](https://developer.android.com/jetpack/androidx/releases/room);

## Métodos utilizados

Foi utilizado os seguintes métodos da api do [The Movie DB](https://api.themoviedb.org/3/):

- GET => trending/{type}/{period}
```json
{
    "page": 1,
    "results": [
        {
            "adult": false,
            "backdrop_path": "/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg",
            "genre_ids": [
                28,
                12,
                14,
                35
            ],
            "id": 436969,
            "original_language": "en",
            "original_title": "The Suicide Squad",
            "overview": "Os supervilões Harley Quinn (Margot Robbie), Bloodsport (Idris Elba), Peacemaker (John Cena) e uma coleção de malucos condenados da prisão de Belle Reve juntam-se à super-secreta e super-obscura Força Tarefa X, enquanto são deixados na remota ilha de Corto Maltese para combater o inimigo.",
            "poster_path": "/wTS3dS2DJiMFFgqKDz5fxMTri.jpg",
            "release_date": "2021-07-28",
            "title": "O Esquadrão Suicida",
            "video": false,
            "vote_average": 8.0,
            "vote_count": 3511,
            "popularity": 3075.608,
            "media_type": "movie"
        }
    ]
}
```
- GET => search/{type}
```json
{
    "page": 1,
    "results": [
        {
            "adult": false,
            "backdrop_path": "/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg",
            "genre_ids": [
                28,
                12,
                14,
                35
            ],
            "id": 436969,
            "original_language": "en",
            "original_title": "The Suicide Squad",
            "overview": "Os supervilões Harley Quinn (Margot Robbie), Bloodsport (Idris Elba), Peacemaker (John Cena) e uma coleção de malucos condenados da prisão de Belle Reve juntam-se à super-secreta e super-obscura Força Tarefa X, enquanto são deixados na remota ilha de Corto Maltese para combater o inimigo.",
            "popularity": 3075.608,
            "poster_path": "/wTS3dS2DJiMFFgqKDz5fxMTri.jpg",
            "release_date": "2021-07-28",
            "title": "O Esquadrão Suicida",
            "video": false,
            "vote_average": 8,
            "vote_count": 3511
        }
    ]
}
```
- GET => movie/{id}
```json
{
    "adult": false,
    "backdrop_path": "/jlGmlFOcfo8n5tURmhC7YVd4Iyy.jpg",
    "belongs_to_collection": {
        "id": 531242,
        "name": "Esquadrão Suicida - Coletânea",
        "poster_path": "/bdgaCpdDh0J0H7ZRpDP2NJ8zxJE.jpg",
        "backdrop_path": "/e0uUxFdhdGLcvy9eC7WlO2eLusr.jpg"
    },
    "budget": 180000000,
    "genres": [
        {
            "id": 28,
            "name": "Ação"
        },
        {
            "id": 12,
            "name": "Aventura"
        },
        {
            "id": 14,
            "name": "Fantasia"
        },
        {
            "id": 35,
            "name": "Comédia"
        }
    ],
    "homepage": "https://www.thesuicidesquad.net",
    "id": 436969,
    "imdb_id": "tt6334354",
    "original_language": "en",
    "original_title": "The Suicide Squad",
    "overview": "Os supervilões Harley Quinn (Margot Robbie), Bloodsport (Idris Elba), Peacemaker (John Cena) e uma coleção de malucos condenados da prisão de Belle Reve juntam-se à super-secreta e super-obscura Força Tarefa X, enquanto são deixados na remota ilha de Corto Maltese para combater o inimigo.",
    "popularity": 3075.608,
    "poster_path": "/wTS3dS2DJiMFFgqKDz5fxMTri.jpg",
    "production_companies": [
        {
            "id": 9993,
            "logo_path": "/2Tc1P3Ac8M479naPp1kYT3izLS5.png",
            "name": "DC Entertainment",
            "origin_country": "US"
        },
        {
            "id": 128064,
            "logo_path": "/13F3Jf7EFAcREU0xzZqJnVnyGXu.png",
            "name": "DC Films",
            "origin_country": "US"
        },
        {
            "id": 507,
            "logo_path": "/z7H707qUWigbjHnJDMfj6QITEpb.png",
            "name": "Atlas Entertainment",
            "origin_country": "US"
        },
        {
            "id": 429,
            "logo_path": "/2Tc1P3Ac8M479naPp1kYT3izLS5.png",
            "name": "DC Comics",
            "origin_country": "US"
        },
        {
            "id": 11565,
            "logo_path": null,
            "name": "The Safran Company",
            "origin_country": "US"
        },
        {
            "id": 174,
            "logo_path": "/IuAlhI9eVC9Z8UQWOIDdWRKSEJ.png",
            "name": "Warner Bros. Pictures",
            "origin_country": "US"
        },
        {
            "id": 10823,
            "logo_path": null,
            "name": "Argentina Sono Film",
            "origin_country": "AR"
        }
    ],
    "production_countries": [
        {
            "iso_3166_1": "US",
            "name": "United States of America"
        }
    ],
    "release_date": "2021-07-28",
    "revenue": 155379000,
    "runtime": 132,
    "spoken_languages": [
        {
            "english_name": "English",
            "iso_639_1": "en",
            "name": "English"
        },
        {
            "english_name": "Spanish",
            "iso_639_1": "es",
            "name": "Español"
        },
        {
            "english_name": "French",
            "iso_639_1": "fr",
            "name": "Français"
        }
    ],
    "status": "Released",
    "tagline": "Eles estão loucos... para salvar o mundo.",
    "title": "O Esquadrão Suicida",
    "video": false,
    "vote_average": 8.0,
    "vote_count": 3543
}
```
