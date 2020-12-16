<template>
   <v-row justify="center">
    <v-dialog v-model="ActivationDialog" max-width="600px">
      <div class="detailsBorderColor">
        <v-card>
          <v-card-title>
            <span class="primary--text font-italic headline" primary-title
              >Activate your account</span
            >
            <v-spacer></v-spacer>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-form ref="form">
                 <v-btn color="primary" style="  margin-left: 200px;" @click="activate">Activate</v-btn>
              </v-form>
            </v-container>
          </v-card-text>
        </v-card>
      </div>
    </v-dialog>
  </v-row>
</template>

<script>
import axios from "axios";
export default {
      data() {
        return {
            token: '',
            ActivationDialog: true,
            formFields: [],
            taskId: {},
        }
      },
       methods: {
        loadActivationForm() {
          
        },
        activate() {
          this.token = this.$route.params.token;
          console.log(this.token);
          console.log(this.$store.state.processID);
          axios
          .post("http://localhost:8080/activation/" + this.token + "/"  + this.$store.state.processID)
          .then((response) => {
              console.log(response.data);        
          })
          .catch(error => {
              this.poruka = "Doslo je do greske prilikom aktivacije vaseg naloga";
              console.log(error);
          });
        },
        close() {
          this.ActivationDialog = false;
          this.$refs.form.reset();
        },
        
      },
      mounted() {
        console.log(this.$store.state.processID);
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