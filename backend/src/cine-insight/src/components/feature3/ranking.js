// 기존 데이터에 genreRateList가 없으면 빈 리스트 반환
// 기존 데이터는 다음과 같은 형태
// {
//   "genreRateList": [
//     {
//       "genres": [
//         "Drama"
//       ],
//       "averageRating": 3.62988768342414,
//       "frequency": 69
//     },
// {
// averageRating과 frequency의 곱을 계산하여 score로 저장
// 그리고 그 리스트 중 순위가 높은/낮은 10개의 데이터를 반환

import RankingListElem from "./rankingListElem";

function getRanking(data, isHigh) {
    if (!data) {
        return [];
    }

    let ranking = data.map((d) => {
        return {
            genres: d.genres,
            score: d.averageRating * d.frequency,
        };
    });

    ranking.sort((a, b) => {
        return isHigh ? b.score - a.score : a.score - b.score;
    });

    return ranking.slice(0, 10);
}

export default function Ranking({ data }) {
    const highRanking = getRanking(data, true);
    const lowRanking = getRanking(data, false);
    return (
        <div className="flex space-x-4">
            <div className="w-1/2 text-center">
                <h3 className="text-lg font-bold p-2">High Ranking</h3>
                <ul className="flex-col justify-center items-center w-full">
                    {highRanking.map((r, i) => (
                        <RankingListElem
                            key={i}
                            keyElem={i}
                            label={r.genres.join(", ")}
                            score={r.score}
                            bgColor={i === 0 ? "bg-gradient-to-r from-yellow-400 via-yellow-200 to-yellow-500" : 
                                i === 1 ? "bg-gradient-to-r from-slate-300 via-slate-100 to-slate-400" :
                                i === 2 ? "bg-gradient-to-r from-amber-700 via-amber-500 to-amber-600" : ""
                            }
                        />
                    ))}
                </ul>
            </div>
            <div className="w-1/2 text-center">
                <h3 className="text-lg font-bold p-2">Low Ranking</h3>{" "}
                <ul className="flex-col justify-center items-center w-full">
                    {lowRanking.map((r, i) => (
                        <RankingListElem
                            key={i}
                            keyElem={i}
                            label={r.genres.join(", ")}
                            score={r.score}
                            bgColor={i === 0 ? "bg-gradient-to-r from-red-300 via-red-200 to-red-400" : 
                                i === 1 ? "bg-gradient-to-r from-red-200 via-red-100 to-red-300" :
                                i === 2 ? "bg-gradient-to-r from-red-100 via-red-50 to-red-200" : ""
                            }
                        />
                    ))}
                </ul>
            </div>
        </div>
    );
}
