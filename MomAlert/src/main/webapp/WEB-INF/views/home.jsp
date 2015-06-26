<h1>Home 44444</h1>

<p>This is the page intoduce how to do pulse checking for the Srs..</p>

<p>You can add Fitbit, </p>


<div id="fb-root"></div>
<script>
// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.
function backHome() {
  console.log('Welcome!  Fetching your information.... ');
  FB.api('/me', function(response) {
    console.log('Successful login for: ' + response.name);
    document.getElementById('status').innerHTML =
      'Thanks for logging in, ' + response.name + '!';
    
    $("#email").val(response.email);
    $("#name").val(response.name);
    $("#first_name").val(response.first_name);
    $("#gender").val(response.gender);
    $("#id").val(response.id);
    $("#last_name").val(response.last_name);
    $("#link").val(response.link);
    $("#locale").val(response.locale);
    $("#timezone").val(response.timezone);
      
    
    $("#target").submit();
  });
}


</script>


<fb:login-button scope="public_profile,email" onlogin="checkLoginState();"></fb:login-button>

<div id="status" class="demo-container">
</div>

<form id="target" action="/momalert/home" method="POST">
  <input type="hidden" id ="email" name="email"  value="xxxxxxx"/>
  <input type="hidden" id ="first_name" name="first_name"/>
  <input type="hidden" id="gender"name="gender"/>
  <input type="hidden" id="id" name="id"/>
  <input type="hidden" id="last_name" name="last_name"/>
  <input type="hidden" id="link" name="link"/>
  <input type="hidden" id="locale" name="locale"/>
  <input type="hidden" id="name" name="name"/>
  <input type="hidden" id="timezone" name="timezone"/>
  
 </form>


