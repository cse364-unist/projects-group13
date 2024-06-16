export default function RankingListElem({ keyElem, label, score, bgColor }) {    
    return (
        <li key={keyElem} className="p-2">
            <div className={`flex-col p-1 justify-center items-center text-center rounded-lg ${bgColor ? bgColor : "bg-gray-200"} hover:shadow-xl hover:z-10 hover:scale-125 transition-all duration-100`}>
                <div className="font-bold">
                    <label>{label}</label>
                </div>
                <div>
                    <label className="text-gray-500">{score.toFixed(2)}</label>
                </div>
            </div>
        </li>
    );
}
