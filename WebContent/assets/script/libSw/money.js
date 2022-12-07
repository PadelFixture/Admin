function textToMoney($this)
{
	var $num=$this.value;
	$num=formatearAMoneda($num);	
	$this.value=$num;
}
function stringToMoney($valor){
	var num=$num=formatearAMonedaWithoutComa($valor);
	return num;
}
function darFormatoMonedaTexto($valor, $decimales,$simbolo){
	var valor=$valor.toString().replace(/[^0-9]/gi,'');
	var decimales=$decimals;
	var split=valor.substring(valor.length-decimales, valor.length);
	var split2=valor.substring(0, valor.length-decimales);
	split2=parseInt(split2)?parseInt(split2):0;
	var $lenght=split2.toString().length;	
	$string=split2.toString();
	var $substring="";
	var $nAux="";
	var numeroFormateado = [];
	while($lenght>0)
	{			
		    $substring = $string.substring(($string.length-3), $string.length);
		    $string=$string.substring(0,$string.length-3);
		    numeroFormateado.push($substring);
		    $lenght=$lenght-3;
	}
	var numeroFinal=[];
	for(var $i=numeroFormateado.length-1;$i>=0;$i--)
	{	
	if($i==numeroFormateado.length-1)
		{
			$nAux=numeroFormateado[$i];
		}
		else{
			$nAux=$nAux+"."+numeroFormateado[$i];
		}
	}
	var fin=$simbolo+$nAux+","+split;
	return fin;
	
}
function monedaToStringParseado($valor){
	$string=$valor.replace('$','');
	$string=$string.replace(/[\.\$]/gi,'');
	
	return $string;
	
}





function arreglarMoneda($this, $decimals){
	var valor=$this.value.replace(/[^0-9]/gi,'');
	var decimales=$decimals;
	var split=valor.substring(valor.length-decimales, valor.length);
	var split2=valor.substring(0, valor.length-decimales);
	split2=parseInt(split2)?parseInt(split2):0;
	var $lenght=split2.toString().length;	
	$string=split2.toString();
	var $substring="";
	var $nAux="";
	var numeroFormateado = [];
	while($lenght>0)
	{			
		    $substring = $string.substring(($string.length-3), $string.length);
		    $string=$string.substring(0,$string.length-3);
		    numeroFormateado.push($substring);
		    $lenght=$lenght-3;
	}
	var numeroFinal=[];
	for(var $i=numeroFormateado.length-1;$i>=0;$i--)
	{	
	if($i==numeroFormateado.length-1)
		{
			$nAux=numeroFormateado[$i];
		}
		else{
			$nAux=$nAux+"."+numeroFormateado[$i];
		}
	}
	var fin="$"+$nAux+","+split;
	$this.value=fin;
}

function textToMoneyWithoutComa($this)
{
	var $num=$this.value;
	$num=formatearAMonedaWithoutComa($num);	
	$this.value=$num;
}
function withoutDollar()
{
	

}

//utilización:
//ubicar function onkeyup="toUpperCase(this)"

function toUpperCase($input)
{
	
	$("#"+$input.id).val($("#"+$input.id).val().toUpperCase());
}


function moneyToText($money)
{
	var $string=""+$money;
	$string=$string.replace(/[^0-9]/gi,'');
	$string=$string.replace(/[\.\$]/gi,'');
	$string=$string.replace('$','');
	return $string;
}
function formatearAMoneda($integer)
{	
	
	var $numero=$integer;	
	var $string=""+$integer;
	$string=$string.replace(/[^0-9]/gi,'');
	$string=$string.replace(/[\.\$\,]/gi,'');
	$string=$string.replace('$','');
	var $lenght=parseInt($string.length);	
	var $substring="";
	var $nAux="";
	var numeroFormateado = [];
	while($lenght>0)
	{			
		    $substring = $string.substring(($string.length-3), $string.length);
		    $string=$string.substring(0,$string.length-3);
		    numeroFormateado.push($substring);
		    $lenght=$lenght-3;
	}
	var numeroFinal=[];
	for(var $i=numeroFormateado.length-1;$i>=0;$i--)
	{	
	if($i==numeroFormateado.length-1)
		{
			$nAux=numeroFormateado[$i];
		}
		else{
			$nAux=$nAux+"."+numeroFormateado[$i];
		}
	}
	return "$"+$nAux;
}
function formatearAMonedaWithoutComa($integer)
{	
	
	var $numero=$integer;	
	var $string=""+$integer;
	$string=$string.replace(/[^0-9]/gi,'');
	$string=$string.replace(/[\.\$]/gi,'');
	$string=$string.replace('$','');
	$string=""+parseInt($string);
	var $lenght=parseInt($string.length);	
	var $substring="";
	var $nAux="";
	var numeroFormateado = [];
	while($lenght>0)
	{			
		    $substring = $string.substring(($string.length-3), $string.length);
		    $string=$string.substring(0,$string.length-3);
		    numeroFormateado.push($substring);
		    $lenght=$lenght-3;
	}
	var numeroFinal=[];
	for(var $i=numeroFormateado.length-1;$i>=0;$i--)
	{	
	if($i==numeroFormateado.length-1)
		{
			$nAux=numeroFormateado[$i];
		}
		else{
			$nAux=$nAux+"."+numeroFormateado[$i];
		}
	}
	return "$"+$nAux;
}

function formatMonedaWithoutBill($integer)
{
	
	var $numero=$integer;	
	var $string=""+$integer;
	$string=$string.replace(/[^0-9]/gi,'');
	$string=$string.replace(/[\.\$\,]/gi,'');
	$string=$string.replace('$','');
	var $lenght=parseInt($string.length);	
	var $substring="";
	var $nAux="";
	var numeroFormateado = [];
	while($lenght>0)
	{			
		    $substring = $string.substring(($string.length-3), $string.length);
		    $string=$string.substring(0,$string.length-3);
		    numeroFormateado.push($substring);
		    $lenght=$lenght-3;
	}
	var numeroFinal=[];
	for(var $i=numeroFormateado.length-1;$i>=0;$i--)
	{	
	if($i==numeroFormateado.length-1)
		{
			$nAux=numeroFormateado[$i];
		}
		else{
			$nAux=$nAux+"."+numeroFormateado[$i];
		}
	}
	return $nAux;
}


$(".newMoney").keyup(function(e) {
	var e = window.event || e;
	var keyUnicode = e.charCode || e.keyCode;
	if (e !== undefined) {
		switch (keyUnicode) {
			case 16: break; // Shift
			case 17: break; // Ctrl
			case 18: break; // Alt
			case 27: this.value = ''; break; // Esc: clear entry
			case 35: break; // End
			case 36: break; // Home
			case 37: break; // cursor left
			case 38: break; // cursor up
			case 39: break; // cursor right
			case 40: break; // cursor down
			case 78: break; // N (Opera 9.63+ maps the "." from the
							// number key section to the "N" key
							// too!) (See:
							// http://unixpapa.com/js/key.html
							// search for ". Del")
			case 110: break; // . number block (Opera 9.63+ maps
								// the "." from the number block to
								// the "N" key (78) !!!)
			case 190: break; // .
			default: textToMoney(this);
		}
	}
}).blur(function() {
	textToMoney(this);
});