<template>
  <div>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex s12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Organization details</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>

              <v-card-text>
                <v-form :lazy-validation="lazy" ref="form">
                  <v-text-field
                    label="Organization name"
                    name="orgName"
                    :disabled="true"
                    v-model="organizationName"
                    prepend-icon="mdi-account"
                    type="text"
                    :rules="[rules.required]"
                  ></v-text-field>

                  <v-text-field
                    label="Desciption"
                    name="description"
                    v-model="description"
                    prepend-icon="mdi-account"
                    type="text"
                    :rules="[rules.required]"
                  ></v-text-field>

                  <v-file-input
                    ref="iconUpload"
                    prepend-icon="mdi-image"
                    @change="fileSubmited"
                    placeholder="Ikonica organizacije"
                  ></v-file-input>
                </v-form>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="validate" color="blue-grey white--text">Save</v-btn>
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
  data() {
    return {
      organizationName: "",
      description: "",
      organization: null,
      logo: "",
      rules: {
        required: value => !!value || "Required."
      }
    };
  },

  computed: {
    ...mapGetters({
      getUser: "users/getUser",
      getOrgs: "orgs/orgGetter"
    })
  },

  created() {
    this.organizationIndex = this.getOrgs.findIndex(
      o => o.name === this.getUser.organizationName
    );
    this.organization = this.getOrgs[this.organizationIndex];
    this.organizationName = this.organization.name;
    this.description = this.organization.description;
  },

  methods: {
    ...mapActions({
      editOrgAction: "orgs/edit",
      showSnackbar: "snackbar/showSnackbar"
    }),

    fileSubmited(file) {
      this.logo = file.name;
    },

    validate() {
      if (this.$refs.form.validate()) {
        this.submit();
      } else {
        this.showSnackbar(["Morate popuniti sva polja", "error", "bottom"]);
      }
    },

    submit() {
      let changed = false;
      let logoChanged = false;

      if (this.organizationName != this.organization.name) {
        changed = true;
      }

      if (this.description != this.organization.description) {
        changed = true;
      }

      if (this.logo.trim() !== "") {
        logoChanged = true;
        changed = true;
      }

      if (!changed) {
        this.showSnackbar(["Nista niste promenuuuuuli", "error", "bottom"]);
        return;
      }

      let org = {
        name: this.organizationName,
        description: this.description
      };

      if (logoChanged) {
        org.logo = this.logo;
      } else {
        org.logo = this.organization.logo;
      }

      this.editOrgAction([this.organizationIndex, org])
        .then(() => {
          this.showSnackbar(["PromenUUUUli ste organizaciju", "success", "bottom"]);
        })
        .catch(error => alert(error));
    }
  }
};
</script>

<style>
</style>