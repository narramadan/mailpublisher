<template>
  <div id="app">
  	<b-jumbotron header="Email Publisher Client" lead="A simple client to publish email to your choice of recipients" >
	 	<span><b>Note:</b><p>For development & testing purpose only !!!</p></span>
		<p>Press Tab or Comma after providing valid email address in the recipient fields</p>
	</b-jumbotron>
	<router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
