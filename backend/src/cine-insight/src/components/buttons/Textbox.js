export default function Textbox({ children, ...rest }) {
    return (
        <div
            className="inline-block p-2 rounded-xl text-3xl font-bold text-center cursor-pointer hover:shadow-2xl hover:shadow-slate-500 transition-all duration-300"
            {...rest}
        >
            {children}
        </div>
    );
}
