function getCookie(c_name)   
 {
     if (document.cookie.length > 0) {
         c_start = document.cookie.indexOf(c_name + "=");
         if (c_start != -1) { 
             c_start = c_start + c_name.length + 1;
             c_end = document.cookie.indexOf("^",c_start);
             if (c_end==-1)
                 c_end=document.cookie.length;
             return unescape(document.cookie.substring(c_start,c_end));
     } 
   }
     return "";
 }
 
 function setCookie(c_name, n_value, p_name, p_value, expiredays) 
 {
     var exdate = new Date();
     exdate.setDate(exdate.getDate() + expiredays);
     document.cookie = c_name + "=" + escape(n_value) + "^" + p_name + "=" + escape(p_value) + ((expiredays == null) ? "" : "^;expires=" + exdate.toGMTString());
     console.log(document.cookie);
 }
 
 function checkCookie()    
 {
     alert(document.cookie);
     var username = getCookie('username');
     var password = getCookie('password');
     if (username != null && username != "" && password != null && password != "") {
         alert('Your name: ' + username + '\n' + 'Your password: ' + password);
     }
     else {
         username = prompt('Please enter your name:',"");
         password = prompt('Please enter your name:',"");
         if (username != null && username != "" && password != null && password != "")
         {
             setCookie('username', username, 'password', password, 365);
         }
     }
     alert(document.cookie);
 }
 
 function cleanCookie (c_name, p_name) {  
     document.cookie = c_name + "=" + ";" + p_name + "=" + ";expires=Thu, 01-Jan-70 00:00:01 GMT";
 }