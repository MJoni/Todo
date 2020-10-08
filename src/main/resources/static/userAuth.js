let userAuth =document.cookie;
console.log(userAuth)
if(userAuth != null || userAuth != undefined){
    console.log("Session started")
    console.log(userAuth)
}else{
    console.log("Session timed out")
}
