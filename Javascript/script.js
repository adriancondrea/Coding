function createParagraph() {
  let para = document.createElement('p');
  para.textContent = 'You clicked the button!';
  document.body.appendChild(para);
}

const buttons = document.querySelectorAll('button');


var number = generateRandomNumber()
for(let i = 0; i < buttons.length ; i++) {
  buttons[i].addEventListener('click', checkPlayerGuess);
  checkPlayerGuess.number = number;
}



function generateRandomNumber(){
	return Math.floor(Math.random() * 100) + 1;
}



function checkPlayerGuess(number){
	let playerGuess = document.getElementById('guess').value;
	var output;
	if (playerGuess == number){
		output = "You guessed!";
	}
	else if(playerGuess < number)
		output = "Try a greater number!";
	else
		output = "Try a smaller number!";
	let para = document.createElement('p');
	para.textContent = output;
	document.body.appendChild(para);

	let para2 = document.createElement('p');
	para2.textContent = number;
	document.body.appendChild(para2);
}