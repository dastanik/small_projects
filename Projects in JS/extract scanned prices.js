/* 

Created by Dastan Kasmamytov
Extract Prices From the Document 

*/
// This script will scan all pages of the input document
// and extract :

// Output PDF document will be placed in the same folder
// as input. The name of the output document will be:
// Original filename + "_Extracted_SSNs"

var reMatch=/EUR (\d*),*(\d*)/;

var strExt = "_Extracted_Prices.pdf";
//var strIntro = "Prices extracted from document: ";
//var strFinal = "Total number of prices extracted: " ;

ExtractFromDocument(reMatch,strExt);

function ExtractFromDocument(reMatch, strFileExt)
{

var chWord, numWords;
var toMatchBon=/Bon: (\d*)\.(\d*)\.(\d*)/;
var toMatchPAN=/PAN (\d*)/;
var toMatchKarte=/Karte (\d*)/;
var toMatchGueltigkeit=/bis (\d*)\/(\d*)/;
var toMatchIBAN = /IBAN( *)(\r\n*|\r*|\n*)( *)[A-Z][A-Z](\d*)/; 
var toMatchKurzBLZ = /Kurz-BLZ (\d*)/;
var toMatchKto = /Kto\. (\d*)/;
var toMatchBLZOffline = /(\d*) \/ (\d*)( *)(\r\n*|\r*|\n*)( *)Karte /;
var toMatchDatum = /Datum (\d*)\.(\d*)\.(\d*)/;
var toMatchUhr = /(\d*):(\d*) Uhr/;

// construct filename for output document
//var filename = this.path.replace(/\.pdf$/, strFileExt);

// create a report document
try {
 //   var ReportDoc = new Report();
 //   var Out = new Object(); // array where we will collect all our prices before outputing them
    var OutCSV = [" ", "Bon", "Betrag", "PAN", "Karte", "Gültig bis", "IBAN", "Kurz-BLZ", "Kto.", "Datum", "Uhr", "\n"]; //array for CSV

    //ReportDoc.writeText(strMessage1 + this.path);
    //ReportDoc.divide(1);      // draw a horizontal divider
    //ReportDoc.writeText(" "); // write a blank line to output
    
    for (var i = 0; i < this.numPages; i++)
    {
        numWords = this.getPageNumWords(i);
        var PageText = "";
        for (var j = 0; j < numWords; j++) {
            var word = this.getPageNthWord(i,j,false);
            PageText += word;
            }

       // app.alert(PageText);

       	var strBon = PageText.match(toMatchBon);

        var strMatches = PageText.match(reMatch);

        var strPAN = PageText.match(toMatchPAN);

        var strKarte = PageText.match(toMatchKarte);

        var strGueltigkeit = PageText.match(toMatchGueltigkeit);
        //app.alert(strGueltigkeit);

        var strIBAN = PageText.match(toMatchIBAN);

        var strKurzBLZ = PageText.match(toMatchKurzBLZ);

        var strKto = PageText.match(toMatchKto);

        var strBLZOffline = PageText.match(toMatchBLZOffline); 

        var strKtoOffline = PageText.match(toMatchBLZOffline); 

        var strDatum = PageText.match(toMatchDatum); 

        var strUhr = PageText.match(toMatchUhr); 

        if (strBon == null){
                OutCSV.push("null");
            }
            else{
                OutCSV.push(strBon[0].replace("Bon:", ""));
            }

        if (strMatches == null){
            continue;
        }
        else{

             for (j = 0; j < strMatches.length; j++) {
                //Out[strMatches[j]] = true; // store price as a property name
                if (j==0){
                    //change , to .
                    var newStr = strMatches[j].replace("EUR", "");
                    OutCSV.push(newStr.replace(",", "."));
            }
            }

            if (strPAN == null){
                OutCSV.push("null");
            }
            else{
                OutCSV.push(strPAN[0].replace("PAN", ""));
            }

            if (strKarte== null){
                OutCSV.push("null");
            }
            else{
                OutCSV.push(strKarte[0].replace("Karte", ""));
            }

            if (strGueltigkeit== null){
                OutCSV.push("null");
            }
            else{
                OutCSV.push(strGueltigkeit[0].replace("bis", ""));
            }

           // app.alert(strIBAN);

            if (strIBAN== null){
                OutCSV.push("null");
            }
            else{
                //app.alert(strIBAN[0]);
                var newStrIBAN = strIBAN[0].replace(/\s+/g,"");
                OutCSV.push(newStrIBAN.replace("IBAN", ""));
            }

            if (strKurzBLZ== null){
                if (strBLZOffline== null){
                	OutCSV.push("null");
	            }
	            else{
	            	strBLZOffline = strBLZOffline[0].substring(0, 8);
	                OutCSV.push(strBLZOffline);
	            }
            }
            else{
                OutCSV.push(strKurzBLZ[0].replace("Kurz-BLZ", ""));
            }

            if (strKto== null){
                if (strKtoOffline== null){
	                OutCSV.push("null");
	            }
	            else{
	            	strKtoOffline = strKtoOffline[0].substring(10, 21);   
	                OutCSV.push(strKtoOffline);
	            }
            }
            else{
                OutCSV.push(strKto[0].replace("Kto.", "⠀"));
            }


            if (strDatum== null){
                OutCSV.push("null");
            }
            else{
                OutCSV.push(strDatum[0].replace("Datum ", ""));
            }

            if (strUhr== null){
                OutCSV.push("null");
            }
            else{
                OutCSV.push(strUhr[0].replace("Uhr", ""));
            }


            OutCSV.push("\n");
        
        }
        
    }
    
   // app.alert(OutCSV);

    this.createDataObject('output.csv', OutCSV.join());
/*
    var nTotal = 0;
    for (var prop in Out) 
    {
        ReportDoc.writeText(prop);

        nTotal++;
    }*/

    this.exportDataObject({ cName:'output.csv', nLaunch:'2'});
    
    //ReportDoc.writeText(" "); // output extra blank line
    //ReportDoc.divide(1); // draw a horizontal divider
    //ReportDoc.writeText(strMessage2 + nTotal);
    
    // save report to a document
  /* ReportDoc.save(
        {
        cDIPath: filename
        }); */

}
catch(e)
{
app.alert("Processing error: "+e)
}

} // end of the function
