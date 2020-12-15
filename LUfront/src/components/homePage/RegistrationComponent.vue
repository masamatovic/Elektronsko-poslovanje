<template>
  <v-row justify="center">
    <v-dialog v-model="RegisterDialog" max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn text color="primary" v-on="on" @click="loadRegistrationForm()">
          <span>Register</span>
          <RegistrationIcon></RegistrationIcon>
        </v-btn>
      </template>
      <v-card class="detailsBorderColor">
        <v-card-title>
          <span class="primary--text font-italic headline" primary-title>Registration</span>
          <v-spacer></v-spacer>
          <v-btn icon color="primary" @click="RegisterDialog =  false">
            <CloseIcon></CloseIcon>
          </v-btn>
       </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="form">
              <div v-for="field in formFields" :key="field.id"> 
                <v-text-field
                  :label = "field.id"
                  v-if="field.type.name=='string'"
                  v-model="field.fieldValue"
                ></v-text-field>
                <v-checkbox
                  :label = "field.id"
                  v-if="field.type.name=='boolean'"
                  v-model="field.fieldValue"
                  @change="changeGenreBetaVisibility()"
                ></v-checkbox>
                <v-combobox
                  v-if="field.id=='genre'"
                  :items="Object.keys(field.type.values)"
                  :label="field.label"
                  v-model="field.fieldValue"
                  outlined
                  dense
                ></v-combobox>
                <v-combobox
                  v-if="field.id=='genreBeta' && betaReader==true"
                  :items="Object.keys(field.type.values)"
                  :label="field.label"
                  v-model="field.fieldValue"
                  outlined
                  dense
                ></v-combobox>
              </div>
            <!--  <v-text-field
                class="mt-n2"
                label="Name*"
                color="primary"
                v-model="user.name"
                required
                :rules="requiredRules"
              ></v-text-field>
              <v-text-field
                label="Surname*"
                color="primary"
                v-model="user.surname"
                required
                :rules="requiredRules"
              ></v-text-field>
              <v-text-field
                label="Phone number*"
                color="primary"
                v-model="user.number"
                required
                :rules="requiredRules"
              ></v-text-field>
              <v-text-field
                label="Address*"
                color="primary"
                v-model="user.address"
                required
                :rules="requiredRules"
              ></v-text-field>
              <v-text-field
                label="Email*"
                color="primary"
                v-model="user.email"
                required
                :rules="emailRules"
              ></v-text-field>

              <v-text-field
                color="primary"
                label="Password*"
                v-model="user.password"
                type="password"
                required
                :rules="requiredRules"
              ></v-text-field>
              <v-text-field
                color="primary"
                label="Confirm password*"
                v-model="confirmation"
                type="password"
                required
                :rules="[passwordConfirmationRule]"
              ></v-text-field>
              -->
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions class="pr-10 pb-10">
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="close">Cancel</v-btn>
          <v-btn color="primary" @click="register">Register</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import Axios from 'axios';
import RegistrationIcon from 'vue-material-design-icons/AccountCircle.vue'
import CloseIcon from 'vue-material-design-icons/CloseCircle.vue'
export default {
   components: {
    RegistrationIcon, CloseIcon
  },
  data: () => ({
    RegisterDialog: false,
    user: {},
    confirmation: "",
    requiredRules: [v => !!v || "This field is required"],
    passwordRules: [
      v => !!v || "This is required",
      v => v == this.confirmation || "Passwords do not match"
    ],
    emailRules: [
      v => !!v || "This field is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    formFields: [],
    taskId:{},
    genreValues: [],
    selectedGenreValues: [],
    betaReader: false,
    field: {},
  }),
  computed: {
    passwordConfirmationRule() {
      return () =>
        this.user.password === this.confirmation || "Password must match";
    }
  },
  methods: {
    register() {
      if (this.$refs.form.validate()) {
        let formSubmissionDto = new Array();
        this.formFields.forEach(formField => {
          formSubmissionDto.push({id : formField.id, fieldValue : formField.fieldValue})
        });
        Axios
        .post("http://localhost:8080/register/" + this.taskId, formSubmissionDto)
        .then(response => {
            console.log(response)
          })  
        .catch(error => {
            console.log(error)
          })        
        this.close();
      } else {
        console.log("nije validno");
      }
    },
    close() {
      this.RegisterDialog = false;
      this.$refs.form.reset();
    },
    loadRegistrationForm(){
      Axios
      .get("http://localhost:8080/registrationForm")
      .then(response => {
          this.formFields = response.data.formFields;
          this.taskId = response.data.taskId;
        })  
      .catch(error => {
          console.log(error)
        })
    },
    changeGenreBetaVisibility(){
      this.betaReader = !this.betaReader
    }

  },
  mounted(){
    
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