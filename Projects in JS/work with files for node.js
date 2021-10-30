const fs = require('fs');
const {promisify} = require('util');
const csv = require('csvtojson');
const path = require('path');

const writeFile = promisify(fs.writeFile);
let [ filePath, targetPath ] = process.argv.slice(2);

if(!filePath) {
    console.error("Please provide a csv file to convet to JSON");
    process.exit();
}

if(!targetPath) {
    const {dir, name} = path.parse(filePath);
    targetPath = path.join(dir, `${name}.json`);
    console.log(dir);
    console.log(targetPath);
}

/*
csv().fromFile(filePath)
.then(jsonData => writeFile(targetPath, JSON.stringify(jsonData, null, 2)))
.then(()=> console.log('JSON saved here: ${targetPath}'))
.catch(err => console.error("Something went wrong"));*/

async function csvToJson(filePath, targetPath)
{
  try{
    const jsonData = await csv().fromFile(filePath);
    const data = await writeFile(targetPath, JSON.stringify(jsonData, null, 2));
    console.log('JSON saved here');
  }
  catch(err){
    console.error("Something went wrong");
  }
}

csvToJson(filePath, targetPath);

