/** @type {import('tailwindcss').Config} */
module.exports = {
	content: [
		"./src/main/resources/templates/**/*.html"
	],
	theme: {
		extend: {
			colors: {
				'primero': '#643e7b',
				'segundo': '#a594f9',
				'tercero': '#cdc1ff',
				'cuarto': '#f5efff'
			}
		}
	},
	plugins: [],
}

