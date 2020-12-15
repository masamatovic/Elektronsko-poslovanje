<template>
  <v-row justify="center">
     <h1>Dobrodošli, možete ulogovati na Vaš nalog! </h1>
  </v-row>
</template>

<script>
import axios from "axios";
export default {
      data() {
        return {
            token: ''
        }
      },
      mounted () {
        this.token = this.$route.params.token;
        console.log("ovo je token");
        console.log(this.token);
       //localStorage.removeItem("jwt");
         //       this.$store.commit('logout')
        axios
        .post("http://localhost:8080/activation/" + this.token)
        .then((response) => {
            console.log(response.data);        
        })
        .catch(error => {
            this.poruka = "Doslo je do greske prilikom aktivacije vaseg naloga";
            console.log(error);
        });
      },
    }
</script>

<style scoped>
.cardBorderColor {
  border-left: 1px solid #26A69A;
  border-top: 1px solid #26A69A;
  border-right: 1px solid #26A69A;
  border-bottom: 1px solid #26A69A;
}
.detailsBorderColor {
  border-left: 2px solid #26A69A;
  border-top: 2px solid #26A69A;
  border-right: 2px solid #26A69A;
  border-bottom: 2px solid #26A69A;
}
</style>