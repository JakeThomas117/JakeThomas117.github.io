function make2DArray(cols, rows) {
	let arr = new Array(cols);
	for (let i = 0; i < arr.length; i++) {
		arr[i] = new Array(rows);
	}
	return arr;
}

let grid;
let revealed;
let marked;

let width = 600;
let height = 600;
let cols;
let rows;
let resolution = 40;
let bombs = 30;

let endGame;
let gameOver;

function setup() {
	createCanvas(width, height);
	cols = width / resolution;
	rows = height / resolution;

	grid = make2DArray(cols, rows);
	for (let n = 0; n < bombs; n++) {
		let i = floor(random(cols));
		let j = floor(random(rows));
		if (grid[i][j] == -1) {
			n--;
		} else {
			grid[i][j] = -1;
		}
	}
	countNeighbors();

	revealed = make2DArray(rows, cols);
	for (let i = 0; i < cols; i++) {
		for (let j = 0; j < rows; j++) {
			revealed[i][j] = false;
		}
	}

	marked = make2DArray(rows, cols);
	for (let i = 0; i < cols; i++) {
		for (let j = 0; j < rows; j++) {
			marked[i][j] = false;
		}
	}
}

function draw() {
	background(200);

	for (let i = 0; i < cols; i++) {
		for (let j = 0; j < rows; j++) {
			if (revealed[i][j] || marked[i][j]) {
				if (!gameOver)
					endGame++;
			}
		}
	}
	if (endGame == rows * cols) {
		fill(255);
		rect(2, 2, width - 4, height - 4);
		fill(0, 255, 0);
		noStroke();
		textAlign(CENTER);
		textSize(80);
		text("You Win!", width / 2, height / 2);
		endGame = 0;
	} else {
		endGame = 0;
		textSize(12);

		for (let i = 0; i < cols; i++) {
			for (let j = 0; j < rows; j++) {
				let x = i * resolution;
				let y = j * resolution;

				stroke(0);
				noFill();
				rect(x, y, resolution - 1, resolution - 1);

				if (!revealed[i][j] && !marked[i][j]) {
					fill(255);
					rect(x, y, resolution - 1, resolution - 1);
				} else if (!revealed[i][j] && marked[i][j]) {
					fill(255, 0, 0);
					rect(x, y, resolution - 1, resolution - 1);
				} else if (grid[i][j] == -1) {
					fill(200, 0, 0);
					ellipse(x + (resolution / 2), y + (resolution / 2), resolution / 2);
				} else {
					if (grid[i][j] != 0) {
						fill(0);
						textAlign(CENTER);
						text(grid[i][j], x + (resolution / 2), y + (resolution / 2) + 6);
					}
				}
			}
		}
		if (gameOver) {
			fill(255, 0, 0);
			textAlign(CENTER);
			textSize(80);
			text("You Lose!", width / 2, height / 2);
		}
	}
}

function mousePressed() {
	let x = floor(mouseX / resolution);
	let y = floor(mouseY / resolution);
	if (mouseButton == RIGHT) {
		marked[x][y] = true;
	} else if (mouseButton == LEFT) {
		revealed[x][y] = true;
	}
	game(x, y);
}

function game(x, y) {
	if (grid[x][y] == -1 && !marked[x][y]) {
		for (let i = 0; i < cols; i++) {
			for (let j = 0; j < rows; j++) {
				revealed[i][j] = true;
			}
		}
		gameOver = true;
	} else if (grid[x][y] == 0) {
		floodFill(x, y);
	}
}

function floodFill(x, y) {
	for (let xoff = -1; xoff < 2; xoff++) {
		for (let yoff = -1; yoff < 2; yoff++) {
			let i = x + xoff;
			let j = y + yoff;
			if (i > -1 && i < cols && j > -1 && j < rows) {
				if (!revealed[i][j]) {
					revealed[i][j] = true;

					if (grid[i][j] == 0) {
						floodFill(i, j);
					}
				}
			}
		}
	}
}

function countNeighbors() {
	for (let x = 0; x < cols; x++) {
		for (let y = 0; y < rows; y++) {
			if (grid[x][y] != -1) {
				grid[x][y] = 0;
				for (let xoff = -1; xoff < 2; xoff++) {
					for (let yoff = -1; yoff < 2; yoff++) {
						let i = x + xoff;
						let j = y + yoff;
						if (i > -1 && i < cols && j > -1 && j < rows) {
							if (grid[i][j] == -1) {
								grid[x][y]++;
							}
						}
					}
				}
			}
		}
	}
}