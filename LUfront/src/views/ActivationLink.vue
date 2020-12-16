<template>
   <v-row justify="center">
    <v-dialog v-model="ActivationDialog" max-width="600px">
      <div class="detailsBorderColor">
        <v-card>
          <v-card-title>
            <span class="primary--text font-italic headline" primary-title
              >Activation token</span
            >
            <v-spacer></v-spacer>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-form ref="form">
                <div v-for="field in formFields" :key="field.id">
                <v-text-field
                  :label="field.id"
                  v-model="field.fieldValue"
                ></v-text-field>
                </div>
              </v-form>
            </v-container>
          </v-card-text>
          <v-card-actions class="pr-10 pb-10">
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="close">Cancel</v-btn>
            <v-btn color="primary" @click="activate">Activate your account</v-btn>
          </v-card-actions>
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
        close() {
          this.ActivationDialog = false;
          this.$refs.form.reset();
        },
        
      },
      mounted() {
          console.log('usao sam ovde');
          axios
          .get("http://localhost:8080/activationForm")
          .then((response) => {
              this.formFields = response.data.formFields;
              this.taskId = response.data.taskId;
            })
            .catch((error) => {
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