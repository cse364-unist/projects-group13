# 🎬 Cine Insight
> **CSE364 Group Project Group.13**

Cine Insight is a novel project designed to support comprehensive decision-making in the film industry.
Our platform leverages advanced data analysis to provide valuable recommendations for filmmakers, actors, and investors.
By distilling industry data, Cine Insight helps stakeholders make informed, rational decisions to enhance their project outcomes.

## Content

1. [How To Execute?](#how-to-excute)
2. [API Explain](#api-explain)
   1. [Feature1: Preferred User Analysis](#-feature-1-preferred-user-analysis)
   2. [Feature2: Genre-Based Actor Recommendation](#-feature-2-genre-based-actor-recommendation)
   3. [Feature3: Genre Frequency Analysis](#-feature-3-genre-frequency-analysis)
   4. [Movie](#movie) 
   5. [User](#user)
   6. [Rating](#rating)
3. [Appendix](#appendix)
   1. [User API Informations](#user-api-informations)
   2. [For Developers](#for-developers)


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

## API Documentation


### 🍿 Feature 1: Preferred User Analysis

The Preferred User Analysis insights into audience preferences by analyzing top-rated and bottom-rated movies within a specified genre.
It outputs demographic data related to the viewers of these movies, assisting in tailored marketing and content optimization.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Top-Rated Movies </span>
You can request detailed information about the top five highest-rated movies in the specified genres.

```curl
curl -X GET http://localhost:8080/pua/top-rated?genres={genre1},{genre2},...
```

_For example:_

```curl
curl -X GET http://localhost:8080/pua/top-rated?genres=Action,Fantasy,Sci-Fi
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Lowest-Rated Movies </span>

You can request detailed information about the top five lowest-rated movies in the specified genres.

```
curl -X GET http://localhost:8080/pua/lowest-rated?genres={genre1},{genre2},...
```

---

### 🥸 Feature 2: Genre-Based Actor Recommendation

The Genre-Based Actor Recommendation system utilizes data analytics to provide customized actor recommendations for film projects.
This feature is designed to help directors, writers, and producers find actors who not only fit the thematic and stylistic requirements of the film,
but also enhance the overall synergy of the cast.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Find Actor's Information </span>
You can request some information about a specific actor.

```
curl -X GET http://localhost:8080/gbar/find?name={name}
```



#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Actor Recommendation </span>
You can request a JSON-formatted recommendation based on the given information. This includes a vectorized genre of the movie and actor recommendations that fit with the movie.

```
curl -X GET http://localhost:8080/gbar/recommend -H ‘Content-type:application/json’ -d '{"genre": [{genre1}, {genre2}, {genre3}...], "supporter": [{name1}, {name2}...], "synergy":{synergy} "plot": {movie plot}}'
```

_For example:_
```
curl -X POST http://localhost:8080/gbar/recommend -H ‘Content-type:application/json’ -d '{ "genre":[ 27.6, 7.4, 0, 52, 0, 6.6, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ], "synergy" : 20, "supporter" : ["Robert Hays", "John Belushi"], "plot": "plot is here" }'
```

---

### 🖼️ Feature 3: Genre Frequency Analysis

The Genre Frequency Analysis feature provides a comprehensive analysis of genre combinations across a variety of dimensions, including frequency and average rating.
This tool is essential for filmmakers, marketers, and analysts who need to understand genre trends and reception across different eras and contexts.

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> All Genre Frequencies </span>
You can request mapping data with genre combination, its frequency, and its average ratings will be returned.

```
curl -X GET http://localhost:8080/gfa
```

> [!WARNING]
> This will take a lot of times.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Genre Frequencies by Year </span>
You can request mapping data with genre combinations, their frequencies, and average ratings based on the movies screened in a specific `year`. The `year` should be an integer value.

```
curl -X GET http://localhost:8080/gfa/{year}
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Genre Frequencies by Genre </span>
You can request mapping data with genre combinations, their frequencies, and average ratings where a specific `genre` is included in the combination. The `genre` should be a String.

```
curl -X GET http://localhost:8080/gfa/{genre}
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Genre Frequencies by Year and Genre </span>

You can request mapping data with genre combinations, their frequencies, and average ratings where a specific `genre` is included in the combination, based on the movies screened in a specific `year`. Both `year` and `genre` should be specified, with `year` as an integer and `genre` as a String.

```
curl -X GET http://localhost:8080/gfa/{year}/{genre}
```

---


### Movie

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> All Movies </span>
You can search all the movies and query parameters.
For `genre`, you can request like this to get all the movies that has `Animation` and `Action` genres.

```
curl -X GET http://localhost:8080/movies{?year, genre}
```
**Query Parameters:**

| parameters | type | description |
|---|---|---|
| `year` | Integer | The year that movies screened. |
| `genre` | List<String> | The genre that movies have |

_For example:_ 
```
curl -X GET http://localhost:8080/movies?genre=Animation,Action
```

#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Movie </span>
You can access the specific movie by ID.

```
curl -X GET http://localhost:8080/movies/{id}
```



#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Add </span>
You can request the POST operation with this body. `year` field should be an integer. If the ID is already exists, PUT operation is automatically executed.

```
curl -X POST http://localhost:8080/movies -H ‘Content-type:application/json’ -d '{"id": "(movie id)", "title": "(title)", "year": (year), "genres":["Genre1", "Genre2", ...]}'
```
**Request Body:**
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


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/put.png" width=45 style="vertical-align: middle;"> Update </span>
You can request PUT operation with this body. `year` field should be an integer. If the ID does not exist, error would occur.

```
curl -X PUT http://localhost:8080/movies/{id} -H ‘Content-type:application/json’ -d '{"title": "(title)", "year": (year), "genres":["Genre1", "Genre2", ...]}'
```
**Request Body:**
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


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/delete.png" width=45 style="vertical-align: middle;"> Delete </span>

You can request DELETE operation by giving the url with ID.

```
curl -X DELETE http://localhost:8080/movies/{id}
```

> [!WARNING]
> This operation hadn't been tested.

---

### User


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> All Users </span>
You can request GET with and without query parameters. If you don't write query parameters, all user information would be given.

```
curl -X GET http://localhost:8080/users{?gender, age, occupation, postal}
```
**Query parameters:**

| parameters | type | description |
|---|---|---|
| `gender` | Character | The gender of user. `F` or `M` |
| `age` | Integer | The age of the user. Check out the appendix for more information. |
| `occupation` | Integer | The occupation of the user. Check out the appendix for more information. |
| `postal` | String | The postal of the user. |



#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> User </span>
You can access the specific user with the ID.

```
curl -X GET http://localhost:8080/users/{id}
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Add </span>
You can request POST operation with this body. If the ID is already exists, PUT operation is automatically executed. For age and occupation, please checkout [appendix](#appendix).

```
curl -X POST http://localhost:8080/users -H ‘Content-type:application/json’ -d '{"id": "(user id)", "gender": "(F or M)", "age": (age), "occupation" : (occupation), "postal": "(postal)"}'
```
**Request Body:**
```json
{
   "id": "(user id)",
   "gender": "(F or M)",
   "age": (age),
   "occupation" : (occupation),
   "postal": "(postal)"
}
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/put.png" width=45 style="vertical-align: middle;"> Update </span>
You can request PUT operation with this body. If the ID does not exist, error would occur.

```
curl -X PUT http://localhost:8080/users/{id} -H ‘Content-type:application/json’ -d '{"gender": "(F or M)", "age": (age), "occupation" : (occupation), "postal": "(postal)"}'
```
**Request Body:**
```json
{
   "gender": "(F or M)", 
   "age": (age), 
   "occupation" : (occupation), 
   "postal": "(postal)"
}
```



#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/delete.png" width=45 style="vertical-align: middle;"> Delete </span>
You can request DELETE operation by giving the url with ID.


```
curl -X DELETE http://localhost:8080/users/{id}
```
> [!WARNING]
> This operation hadn't been tested.

---

### Rating

> [!NOTE]
> Rating API does not have an operation calling all the rating datas due to the responsing time problem.


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Movies Above Rating </span>
You can search all the movies that its average rating is greater then, or equeal to given `rating`. 

**`rating` should be an Integer between 1 and 5.**
For `genre`, you can request like this to get all the movies that its average rating is greater then, or equal to given `rating` and has `Animation` and `Action` genres.


```
curl -X GET http://localhost:8080/ratings/{rating}{?year, genre}
```

**Query Parameters:**

| parameters | type | description |
|---|---|---|
| `year` | Integer | The year that movies screened. |
| `genre` | List<String> | The genre that movies have |

_For example:_
```curl
curl -X GET http://localhost:8080/ratings/4?genre=Animation,Action
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Rate </span>

You can request the specific rating entry with ID.

```
curl -X GET http://localhost:8080/ratings/id/{id}
```



#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Rate (\w Movie ID) </span>

You can request all the rating entries with movie ID.

```
curl -X GET http://localhost:8080/ratings/movie/{movieId}
```




#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/get.png" width=45 style="vertical-align: middle;"> Rate (\w User ID) </span>
You can request all the rating entries with user ID.

```
curl -X GET http://localhost:8080/ratings/user/{userId}
```




#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/post.png" width=45 style="vertical-align: middle;"> Add </span>
You can request POST operation with this body. `rate` should be an Integer. If the ID is already exists, PUT operation is automatically executed.

```
curl -X POST http://localhost:8080/ratings -H ‘Content-type:application/json’ -d '{"movieId": "(movie id)", "userId": "(user id)", "rate":(rate), "timestamp":"(timestamp)"}'
```
**Request Body:**

```json
{
   "movieId": "(movie id)", 
   "userId": "(user id)", 
   "rate":(rate), 
   "timestamp":"(timestamp)"
}
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/put.png" width=45 style="vertical-align: middle;"> Update </span>

You can request PUT operation with this body. `rate` should be an Integer.
If the ID does not exist, error would occur. ID is the MongoDB's basic ID. It is not a movie ID nor user ID.

```
curl -X PUT http://localhost:8080/ratings/id/{id} -H ‘Content-type:application/json’ -d '{"movieId": "(movie id)", "userId": "(user id)", "rate":(rate), "timestamp":"(timestamp)"}'
```

**Request Body:**
```json
{
   "movieId": "(movie id)", 
   "userId": "(user id)", 
   "rate":(rate), 
   "timestamp":"(timestamp)"
}
```


#### <span style="display: flex; align-items: center; gap: 5px;"> <img src="./assets/img/delete.png" width=45 style="vertical-align: middle;"> Delete </span>

You can request DELETE operation by giving the url with ID.
```
curl -X DELETE http://localhost:8080/ratings/id/{id}
```
> [!WARNING]
> This operation hadn't been tested.


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


### For Developers

Before starting the project, 
checkout _Git Template_ first. If you downloaded this repository on your local, you can simply execute the following command on the root.

```
git config --global commit.template .gitmessage.txt
```

