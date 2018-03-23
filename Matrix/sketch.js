var symbolSize = 20;
var SymbolStreams = [];

function setup() {
	createCanvas(window.innerWidth, window.innerHeight);
	textSize(symbolSize);

	let numStreams = floor(window.innerWidth / symbolSize);

	for (let i = 0; i < numStreams; i++) {
		SymbolStreams[i] = Stream(i * symbolSize, floor(random(height)), random(5, 30), random(1, 3));
	}
}

function draw() {
	background(0);
	stroke(100, 175, 255);
	for (let i = 0; i < SymbolStreams.length; i++) {
		for (let j = 0; j < SymbolStreams[i].length; j++) {
			SymbolStreams[i][j].render(j, SymbolStreams[i].length);
		}
	}
}

function Symbol(x, y, speed) {
	this.x = x;
	this.y = y;
	this.value;
	this.speed = speed;

	this.setToRandSymbol = function() {
		this.value = String.fromCharCode(0x30a0 + round(random(0, 96)));
	}

	this.render = function(i, len) {
		if (i == len - 1 && random(1) > 0.25) {
			fill(200, random(225, 255), 200);
		} else { //if (frameCount % 60 > 45) {
			fill(0, random(150, 255), random(70));
		}
		text(this.value, this.x, this.y);
		this.rain(i, len);

		if (random(1) > 0.95) {
			this.setToRandSymbol();
		}
	}

	this.rain = function(i, len) {
		if (this.y - (i * symbolSize) > height) {
			this.y -= (height + (len * symbolSize));
		} else {
			this.y += this.speed;
		}
	}
}

function Stream(x, y, length, speed) {
	let stream = [];
	for (let i = 0; i < length; i++) {
		let s = new Symbol(x, y + (i * symbolSize), speed);
		s.setToRandSymbol();
		stream.push(s);
	}
	return stream;
}