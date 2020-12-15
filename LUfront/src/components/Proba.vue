<template>
  <v-row justify="center">
    <v-dialog v-model="LoginDialog" max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn class="mx-1" text color="primary" v-on="on">
          <span>Probaaa</span>
          <LoginIcon right>lock_open</LoginIcon>
        </v-btn>
      </template>
      <div class="detailsBorderColor">
      <v-card>
        <v-card-title>
          <span class="primary--text font-italic headline" primary-title>Proba</span>
          <v-spacer></v-spacer>
          <v-btn icon color="primary" @click="LoginDialog =  false">
            <CloseIcon></CloseIcon>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="form">
              <v-text-field
                label="Email*"
                color="primary"
                v-model="customer.email"
                required
              ></v-text-field>

              <v-text-field
                color="primary"
                label="Password*"
                v-model="customer.password"
                type="password"
                required
              ></v-text-field>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions class="pr-10 pb-10">
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="close">Cancel</v-btn>
          <v-btn color="primary" @click="register">Register</v-btn>
        </v-card-actions>
      </v-card>
      </div>
    </v-dialog>
  </v-row>
</template>

<script>
import Axios from 'axios';
import LoginIcon from 'vue-material-design-icons/Login.vue'
import CloseIcon from 'vue-material-design-icons/CloseCircle.vue'
export default {
  components: {
    LoginIcon, CloseIcon
  },
  data: () => ({
    customer : {
        email: "",
        password: ""
    },
    LoginDialog: false,
  }),
  methods: {
    register() {
        console.log("klikcem i radi");

        Axios
        .post("http://localhost:8080/registerTest",  this.customer)
        .then(response => {
            console.log(response.data)
          })  
        .catch(error => {
            console.log(error)
          })   

    },
    close() {
      this.LoginDialog = false;
      this.$refs.form.reset();
    }
  }
};
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