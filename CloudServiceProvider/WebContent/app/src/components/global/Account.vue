<template>
  <div>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex s12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Licni podaci</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>

              <v-card-text>
                <v-form>
                  <v-text-field
                    label="First Name"
                    name="name"
                    v-model="firstName"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    label="Last Name"
                    name="lastname"
                    v-model="lastName"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Enter the new password"
                    name="password"
                    v-model="password1"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>

                  <v-text-field
                    id="password2"
                    label="Repeat the new password"
                    name="password"
                    v-model="password2"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>
                </v-form>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="submit" color="blue-grey white--text">Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  components: {},

  data() {
    return {
      firstName: "",
      lastName: "",
      password1: "",
      password2: ""
    };
  },

  computed: {
    ...mapGetters({
      getUser: "users/getUser"
    })
  },

  created(){
    this.firstName = this.getUser.firstName;
    this.lastName = this.getUser.lastName;
  },

  methods: {
    ...mapActions({
      changeUserAction: "users/updateAccount"
    }),

    submit() {
      let userInformationChanged = false;
      let passwordChanged = false;
      if (this.getUser.firstName != this.firstName) {
        userInformationChanged = true;
      }

      if (this.getUser.lastName != this.lastName) {
        userInformationChanged = true;
      }

      if (this.password1.trim() != "") {
        userInformationChanged = true;
        passwordChanged = true;

        if (this.password1 != this.password2) {
          alert("Sifre se ne poklapaju");
          return;
        }
      }

      if (!userInformationChanged) {
        alert("Nista niste promenUUUUli");
        return;
      }

      let user = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.getUser.email,
        role: this.getUser.role,
        organizationName: this.getUser.organizationName
      };

      if (passwordChanged) {
        user.password = this.password1;
      }

      this.changeUserAction(user)
        .then(() => {
          alert("PromenUUUUli ste svoj nalog");
          this.$router.go();
          this.$router.push("/");
        })
        .catch(error => alert(error));
    }
  }
};
</script>

