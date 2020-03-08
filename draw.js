var canv;
var canvRect;
var ctx;		var drawing = false;
var rad = 5;
var p = {
	x: 0,
	y: 0
};		

window.onload = function () {
	canv = document.getElementById("gc");
	ctx = canv.getContext("2d");			$('body').on('contextmenu', '#gc', function (e) {
		return false;
	});			document.addEventListener("mousedown", mouseDown);
	document.addEventListener("mousemove", mouseMove);
	document.addEventListener("mouseup", mouseUp);			ctx.fillStyle = "#757575";
	ctx.fillRect(0, 0, canv.width, canv.height);
}		

function mouseDown(evt) {
	drawing = true;
	canvRect = canv.getBoundingClientRect();
	p.x = evt.clientX - canvRect.x;
	p.y = evt.clientY - canvRect.y;			switch (evt.button) {
		case 0:
			ctx.strokeStyle = "#FFFFFF";
			ctx.fillStyle = "#FFFFFF";
			rad = 5;
			break;
		case 2:
			ctx.strokeStyle = "#757575";
			ctx.fillStyle = "#757575";
			rad = 15;
			break;
	}			ctx.beginPath();
	ctx.arc(evt.clientX - canvRect.x, evt.clientY - canvRect.y, rad, 0, 2 * Math.PI);
	ctx.fill();
}

function mouseUp(evt) {
	drawing = false;
}

function mouseMove(evt) {
	if (drawing) {
		ctx.lineWidth = rad * 2 + 1;
		ctx.beginPath();
		ctx.moveTo(p.x, p.y);
		ctx.lineTo(evt.clientX - canvRect.x, evt.clientY - canvRect.y);
		ctx.stroke();				ctx.beginPath();
		ctx.arc(evt.clientX - canvRect.x, evt.clientY - canvRect.y, rad, 0, 2 * Math.PI);
		ctx.fill();				p.x = evt.clientX - canvRect.x;
		p.y = evt.clientY - canvRect.y;
    }
}