# CSE364 Group Project - G.13

## Content

1. [Before Staring The Project](#before-starting-the-project)
2. [How To Execute?](#how-to-excute)
3. [API Explain](#api-explain)
   1. [Movie](#movie)
   2. [User](#user)
   3. [Rating](#rating)
   4. [Feature1: Preferred User Analysis](#-feature-1-preferred-user-analysis)
   5. [Feature2: Genre-Based Actor Recommendation](#-feature-2-genre-based-actor-recommendation)
   6. [Feature3: Genre Frequency Analysis](#-feature-3-genre-frequency-analysis)
4. [Appendix](#appendix)
   1. [User API Informations](#user-api-informations)

---

## Before starting the project...

Checkout _Git Template_ first. If you downloaded this repository on your local, you can simply execute the following command on the root.

```
git config --global commit.template .gitmessage.txt
```

## How to excute?

1. Download the zip file from this repository.
2. In there, type this:
   ```bash
   docker build -t (your custom name) . 
   ```

> [!WARNING]
> DON'T FORGET THE DOT AT THE END!!

4. Wait until the build ends.
5. After the build ended, type this:

   ```bash
   docker run -it (your custom name)
   ```
6. If it runs succesfully, then this command line should appear.
   ```
   root@(containerId):~/project#
   ```
7. Enter `sh run.sh` or `bash run.sh` and wait until you see this:
   ```
   Movies Database has been loaded.
   ```
> [!NOTE]
> This takes at least 50 seconds. Wait until all the database loaded. If you call any API before this done, nothing would work.
8. Open another terminal and type this:
   ```
   docker exec -it (your custom name) /bin/bash
   ```
9. And now you can use `curl`.

## API Explain
### Movie

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> All Movies </span>
##### Curl
```
curl -X GET http://localhost:8080/movies{?year, genre}
```
##### Query Parameters
| parameters | type | description |
|---|---|---|
| `year` | Integer | The year that movies screened. |
| `genre` | List<String> | The genre that movies have |

##### Description
You can search all the movies and query parameters.
For `genre`, you can request like this to get all the movies that has `Animation` and `Action` genres.
```curl
curl -X GET http://localhost:8080/movies?genre=Animation,Action
```

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Movie </span>
##### Curl
```
curl -X GET http://localhost:8080/movies/{id}
```

##### Description
You can access the specific movie by ID.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Add </span>
##### Curl
```
curl -X POST http://localhost:8080/movies -H ‘Content-type:application/json’ -d '{"id": "(movie id)", "title": "(title)", "year": (year), "genres":["Genre1", "Genre2", ...]}'
```
##### Request Body
```json
{
   "id": "(movie id)",
   "title": "(title)", 
   "year": (year), 
   "genres":
      [
         "Genre1", 
         "Genre2", 
         ...
      ]
}
```

##### Description
You can request the POST opperation with this body. `year` field should be an integer. If the ID is already exists, PUT opperation is automatically executed.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/put.png" width=45 style="vertical-align: middle;"> Update </span>
##### Curl
```
curl -X PUT http://localhost:8080/movies/{id} -H ‘Content-type:application/json’ -d '{"title": "(title)", "year": (year), "genres":["Genre1", "Genre2", ...]}'
```
##### Request Body
```json
{
   "title": "(title)", 
   "year": (year), 
   "genres":
      [
         "Genre1", 
         "Genre2", 
         ...
      ]
}
```

##### Description
You can request PUT opperation with this body. `year` field should be an integer. If the ID does not exist, error would occur.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/delete.png" width=45 style="vertical-align: middle;"> Delete </span>

##### Curl
```
curl -X DELETE http://localhost:8080/movies/{id}
```
##### Description

> [!WARNING]
> This opperation hadn't been tested.

You can request DELETE opperation by giving the url with ID.

---

### User


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> All Users </span>

##### Curl
```
curl -X GET http://localhost:8080/users{?gender, age, occupation, postal}
```
##### Query Parameters
| parameters | type | description |
|---|---|---|
| `gender` | Character | The gender of user. `F` or `M` |
| `age` | Integer | The age of the user. Check out the appendix for more information. |
| `occupation` | Integer | The occupation of the user. Check out the appendix for more information. |
| `postal` | String | The postal of the user. |

##### Description
You can request GET with and without query parameters. If you don't write query parameters, all user information would be given.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> User </span>

##### Curl
```
curl -X GET http://localhost:8080/users/{id}
```

##### Description
You can access the specific user with the ID.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Add </span>
##### Curl
```
curl -X POST http://localhost:8080/users -H ‘Content-type:application/json’ -d '{"id": "(user id)", "gender": "(F or M)", "age": (age), "occupation" : (occupation), "postal": "(postal)"}'
```
##### Request Body
```json
{
   "id": "(user id)",
   "gender": "(F or M)",
   "age": (age),
   "occupation" : (occupation),
   "postal": "(postal)"
}
```

##### Description
You can request POST opperation with this body. If the ID is already exists, PUT opperation is automatically executed. For age and occupation, please checkout [appendix](#appendix).

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/put.png" width=45 style="vertical-align: middle;"> Update </span>

##### Curl
```
curl -X PUT http://localhost:8080/users/{id} -H ‘Content-type:application/json’ -d '{"gender": "(F or M)", "age": (age), "occupation" : (occupation), "postal": "(postal)"}'
```
##### Request Body
```json
{
   "gender": "(F or M)", 
   "age": (age), 
   "occupation" : (occupation), 
   "postal": "(postal)"
}
```

##### Description
You can request PUT opperation with this body. If the ID does not exist, error would occur.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/delete.png" width=45 style="vertical-align: middle;"> Delete </span>

##### Curl
```
curl -X DELETE http://localhost:8080/users/{id}
```
> [!WARNING]
> This opperation hadn't been tested.

##### Description
You can request DELETE opperation by giving the url with ID.

---

### Rating

> [!NOTE]
> Rating API does not have an opperation calling all the rating datas due to the responsing time problem.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Movies Above Rating </span>

##### Curl
```
curl -X GET http://localhost:8080/ratings/{rating}{?year, genre}
```

##### Query Parameters
| parameters | type | description |
|---|---|---|
| `year` | Integer | The year that movies screened. |
| `genre` | List<String> | The genre that movies have |

##### Description
You can search all the movies that its average rating is greater then, or equeal to given `rating`. **`rating` should be an Integer between 1 and 5.**
For `genre`, you can request like this to get all the movies that its average rating is greater then, or equal to given `rating` and has `Animation` and `Action` genres.
```curl
curl -X GET http://localhost:8080/ratings/4?genre=Animation,Action
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Rate </span>

##### Curl
```
curl -X GET http://localhost:8080/ratings/id/{id}
```
##### Description
You can request the specific rating entry with ID.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Rate (\w Movie ID) </span>

##### Curl
```
curl -X GET http://localhost:8080/ratings/movie/{movieId}
```
##### Description
You can request all the rating entries with movie ID.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Rate (\w User ID) </span>

##### Curl
```
curl -X GET http://localhost:8080/ratings/user/{userId}
```
##### Description
You can request all the rating entries with user ID.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Add </span>

##### Curl
```
curl -X POST http://localhost:8080/ratings -H ‘Content-type:application/json’ -d '{"movieId": "(movie id)", "userId": "(user id)", "rate":(rate), "timestamp":"(timestamp)"}'
```
##### Request Body
```json
{
   "movieId": "(movie id)", 
   "userId": "(user id)", 
   "rate":(rate), 
   "timestamp":"(timestamp)"
}
```

##### Description
You can request POST opperation with this body. `rate` should be an Integer. If the ID is already exists, PUT opperation is automatically executed.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/put.png" width=45 style="vertical-align: middle;"> Update </span>

##### Curl
```
curl -X PUT http://localhost:8080/ratings/id/{id} -H ‘Content-type:application/json’ -d '{"movieId": "(movie id)", "userId": "(user id)", "rate":(rate), "timestamp":"(timestamp)"}'
```

##### Request Body
```json
{
   "movieId": "(movie id)", 
   "userId": "(user id)", 
   "rate":(rate), 
   "timestamp":"(timestamp)"
}
```

##### Description
You can request PUT opperation with this body. `rate` should be an Integer. If the ID does not exist, error would occur. ID is the MongoDB's basic ID. It is not a movie ID nor user ID.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/delete.png" width=45 style="vertical-align: middle;"> Delete </span>

##### Curl
```
curl -X DELETE http://localhost:8080/ratings/id/{id}
```
> [!WARNING]
> This opperation hadn't been tested.

##### Description
You can request DELETE opperation by giving the url with ID.

---

### 🍿 Feature 1: Preferred User Analysis

This feature provides insights into audience preferences by analyzing top-rated and bottom-rated movies within a specified genre.
It outputs demographic data related to the viewers of these movies, assisting in tailored marketing and content optimization.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Top-Rated Movies </span>

##### Curl
```
curl -X GET http://localhost:8080/pua/top-rated?genre={genre1},{genre2},...
```
##### Description
It returns detailed information about the top five highest-rated movies in the specified genres.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Lowest-Rated Movies </span>

##### Curl
```
curl -X GET http://localhost:8080/pua/lowest-rated?genre={genre1},{genre2},...
```
##### Description
It returns detailed information about the top five lowest-rated movies in the specified genres.

### 🥸 Feature 2: Genre-Based Actor Recommendation

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Find Actor's Information </span>
##### Curl
```
curl -X GET http://localhost:8080/gbar/find?name={name}
```

##### Description
It returns some information about received actor.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Actor Recommendation </span>
##### Curl
```
curl -X GET http://localhost:8080/gbar/recommend -H ‘Content-type:application/json’ -d '{"genre": [{genre1}, {genre2}, {genre3}...], "supporter": [{name1}, {name2}...], "synergy":{synergy} "plot": {movie plot}}'
```
##### Curl Example
```
curl -X POST http://localhost:8080/gbar/recommend -H ‘Content-type:application/json’ -d '{ "genre":[ 27.6, 7.4, 0, 52, 0, 6.6, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ], "synergy" : 20, "supporter" : ["Robert Hays", "John Belushi"], "plot": "plot is here" }'
```

##### Description
It returns json formatted recommendation based on given information. Vectorized genre of movie will given, some actors that fit with movie will be returned.


### 🖼️ Feature 3: Genre Frequency Analysis

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> All Genre Frequencies </span>
##### Curl
```
curl -X GET http://localhost:8080/gfa
```

> [!WARNING]
> This will take a lot of times.

##### Description
Map data with genre combination, its frequency, and its average ratings will be returned.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Genre Frequencies by Year </span>
##### Curl
```
curl -X GET http://localhost:8080/gfa/{year}
```

##### Description
Map data with genre combination, its frequency, and its average ratings based on the movies screened in `year`. `year` should be an integer value.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Genre Frequencies by Genre </span>
##### Curl
```
curl -X GET http://localhost:8080/gfa/{genre}
```

##### Description
Map data with genre combination, its frequency, and its average ratings which `genre` is included in combination. `genre` should be a `String`.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Genre Frequencies by Year and Genre </span>
##### Curl
```
curl -X GET http://localhost:8080/gfa/{year}/{genre}
```

##### Description
Map data with genre combination, its frequency, and its average ratings which `genre` is included in combination based on the movies screened in `year`. `year` should be an integer, and `genre` should be a `String`.


## Appendix

### User API informations.
- Age is chosen from the following ranges:

| value | meaning |
| --- | --- |
| 1 | Under 18 |
| 18 | 18 - 24 |
| 25 | 25 - 34 |
| 35 | 35 - 44 |
| 45 | 45 - 54 |
| 56 | 56 + |

- Occupation is chosen from the following choices:

| value | meaning |
| --- | --- |
| 0 |  other or not specified |
| 1 | academic/educator |
| 2 | artist |
| 3 | clerical/admin |
| 4 | college/grad student |
| 5 | customer service |
| 6 | doctor/health care |
| 7 | executive/managerial |
| 8 | farmer |
| 9 | homemaker |
| 10 | K-12 student |
| 11 | lawyer |
| 12 | programmer |
| 13 | retired |
| 14 | sales/marketing |
| 15 | scientist |
| 16 | self-employed |
| 17 | technician/engineer |
| 18 | tradesman/craftsman |
| 19 | unemployed |
| 20 | writer |
