/** @type {import('tailwindcss').Config} */
module.exports = {
	content: [
		"./src/main/resources/templates/**/*.html"
	],
	theme: {
		fontFamily: {
			"sans": ['"Source Sans 3"']
		},
		extend: {
			colors: {
				'eminence': '#643e7b',
				'tropical': '#a594f9',
				'periwinkle': '#cdc1ff',
				'magnolia': '#f5efff'
			},
			// fontFamily: {
				
			// }
		}
	},
	plugins: [],
}

