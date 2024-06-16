/* isSingle: Only-one-choice mode: true, else: false */
export default function GenreSelection({ value, setValue, isSingle = false }) {
    const genres = [
        "Action",
        "Adventure",
        "Animation",
        "Children's",
        "Comedy",
        "Crime",
        "Documentary",
        "Drama",
        "Fantasy",
        "Film-Noir",
        "Horror",
        "Musical",
        "Mystery",
        "Romance",
        "Sci-Fi",
        "Thriller",
        "War",
        "Western",
    ];

    const whenSelcted = "bg-blue-500 text-white shadow-md shadow-blue-400";
    const whenNotSelected = "bg-gray-300 text-gray-800";

    return (
        <div className="border p-4 flex flex-wrap">
            {genres.map((genre) => (
                <button
                    key={genre}
                    className={`px-4 py-2 m-1 rounded-lg transition-all duration-300 ${
                        !value || !value.includes(genre)
                            ? whenNotSelected
                            : whenSelcted
                    }`}
                    onClick={() => {
                        setValue((prevGenres) => {
                            if (prevGenres.includes(genre)) {
                                return prevGenres.filter((g) => g !== genre);
                            } else {
                                return isSingle
                                    ? [genre]
                                    : [...prevGenres, genre];
                            }
                        });
                    }}
                >
                    {genre}
                </button>
            ))}
        </div>
    );
}
