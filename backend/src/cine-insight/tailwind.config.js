/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*"],
  theme: {
    extend: {
      spacing: {
        72: "18rem", // 288px
        80: "20rem", // 320px
        96: "24rem", // 384px
      },
    },
  },
  plugins: [],
};
