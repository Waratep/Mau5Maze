var querystring = require("querystring"),
	fs = require("fs"),
	formidable = require("formidable"),
	util = require('util'),
	exec = require('child_process').exec;


function start(response, postData) {
	console.log("Request handler 'start' was called");
	
	var body = '<html>'+
		'<head>'+
		'<meta http-equiv="Content-Type" content="text/html; '+
		'charset=UTF-8" />'+
		'</head>'+
		'<body>'+
		'<form action="/display" enctype="multipart/form-data" method="post">'+
		'<input type="text" name="dimx"><br>'+
		'<input type="text" name="dimy"><br>'+
	    '<input type="submit" value="Submit">'+
	    '</form>'+
		'</body>'+
		'</html>';
		
	response.writeHead(200, {"Content-Type": "text/html"});
	response.write(body);
	response.end();
}



function display(response, request) {
	console.log("Request handler 'display' was called");
	
	var fileindex = global.filenum+1;
	
	var form = new formidable.IncomingForm();
	console.log("about to parse");
	form.parse(request, function(error, fields, files) {
		console.log("parsing done");
		
		exec("sh target/bin/solver "+fields.dimx+" "+fields.dimy, function(error, stdout, stderr) {
			
		
			var result = stdout.replace(/(\r\n|\n|\r)/gm, "\\n");
			var resultArray = stdout.split("/");
			var mazeResult = '<html>'+
				'<head>'+
				'<meta http-equiv="Content-Type" content="text/html; '+
				'charset=UTF-8" />'+
				'<script>'+
					'var resultData = "'+result+'";'+
					'var resultArray = resultData.split("/");'+
					'var div;'+
					'function updateMaze(mazeData){div.innerHTML = mazeData;}'+
					'function solveMaze(){'+
						'document.getElementById("solve").style.display = "none";'+
						'document.getElementById("restart").style.display = "inline";'+
						'div = document.getElementById("maze");'+
						'var i = 1;'+
						'function loopMaze(){'+
							'updateMaze(resultArray[i]);'+
							'i++;'+ 
							'if(i < resultArray.length-1)'+
							'{'+
								'setTimeout(function() {loopMaze(); }, 1000);'+
							'}'+
						'}'+
						'loopMaze();'+
						'console.log("ASD");'+
					'}'+
				'</script>'+
				'<link rel="stylesheet" type="text/css" href="style.css">'+
				'</head>'+
				'<body>'+
				'<pre id="maze">'+resultArray[0]+'</pre>'+
				'<a id="solve" onclick="solveMaze()">Solve Maze</a>'+
				'<a id="restart" href="/start" style="display:none;">Create New Maze</a>'+
				'</body>'+
				'</html>';
		
			response.writeHead(200, {"Content-Type": "text/html"});
			response.write("Randomly Generated Maze: <br/>");
			response.write(mazeResult);
			response.end();
		});
	});
}

exports.start = start;
exports.display = display;