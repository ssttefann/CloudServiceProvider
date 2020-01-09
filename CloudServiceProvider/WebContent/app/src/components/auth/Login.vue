<template>
  <div>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>

              <v-card-text>
                <v-form>
                  <v-text-field
                    label="Email"
                    name="Email"
                    v-model="email"
                    prepend-icon="mdi-account"
                    type="text"
                    :rules="[rules.required]"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Password"
                    v-model="password"
                    name="password"
                    prepend-icon="mdi-lock"
                    type="password"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-form>
              </v-card-text>

              <v-card-actions>
                <router-link to="/register">
                  <v-btn color="blue-grey white--text">Don't have an account?</v-btn>
                </router-link>

                <v-spacer></v-spacer>
                <v-btn @click="submit" color="blue-grey white--text">Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  components: {},
  data() {
    return {
      email: null,
      password: null,
      rules: {
        required: value => !!value || "Required."
      }
    };
  },

  computed: {
    ...mapGetters({
      isLogged: "users/isLogged",
      isAdmin: "users/isAdmin"
    })
  },

  mounted() {
    //ako je vec ulogovan redirektuj ga
    if (this.isLogged) {
      if (this.isAdmin) {
        this.$router.push("/admin");
      } else {
        this.$router.push("/dashboard");
      }
    }
  },
  methods: {
    ...mapActions({
      logUser: "users/logUser"
    }),
    /**
     * Proverava da li je svako polje forme popunjeno
     * @returns {String} "OK" ako su uslovi zadovoljeni, ako nisu onda imena labela koje ne zadovoljavaju uslov
     */
    validate() {
      let invalid = [];

      if (!this.email) invalid.push("Email");

      if (!this.password) invalid.push("Password");

      let retval = invalid.length > 0 ? invalid.join(",") : "OK";

      return retval;
    },

    /**
     * Poziva se kada korisnik stisne na dugme za login
     */
    submit() {
      let resp = this.validate();

      // ako je RESP ok mozes poslati serveru
      if (resp === "OK") {
        let credentials = {
          password: this.password,
          email: this.email
        };
        this.logUser(credentials)
          .then(user => {
            if (!["User", "Admin", "SuperAdmin"].includes(user.role)) {
              alert("Pogresna kombinacija user/pass");
              return;
            }

            if (this.isAdmin) {
              this.$router.push("/admin");
            } else if (this.isLogged) {
              this.$router.push("/dashboard");
            }
          })
          .catch(error => {
            alert(error);
          });
      } else {
        alert("Labele " + resp + " moraju biti popunjene pre prijavljivanja");
      }
    }
  }
};
</script>


<style scoped>
a {
  text-decoration: none;
}
</style>