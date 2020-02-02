
<template>
  <nav>
    <v-app-bar app class="blue-grey darken-1">
      <v-btn @click="toggleDrawer" :hidden="isDisabled" target="_blank" text large>
        <v-icon color="white">mdi-view-headline</v-icon>
      </v-btn>

      <v-btn @click="homeButon" class="d-flex align-center text-none" text>
        <v-img
          alt="Logo"
          class="shrink mr-2"
          contain
          src="http://icons.iconarchive.com/icons/graphicloads/polygon/256/clouds-2-icon.png"
          transition="scale-transition"
          width="40"
        />
        <span class="display-1 font-weight-light	">
          <span class="orange--text">cloud</span>
          <span class="blue--text">Trek</span>
        </span>
      </v-btn>

      <v-spacer></v-spacer>

      <!-- DUGMICI NA TOOLBARU -->
      <v-btn href="https://youtu.be/dQw4w9WgXcQ?t=43" target="_blank" text>
        <v-icon color="white" size="20">mdi-help-circle</v-icon>
      </v-btn>

      <v-btn href="https://youtu.be/dQw4w9WgXcQ?t=43" target="_blank" text>
        <v-icon color="white" size="20">mdi-bell</v-icon>
      </v-btn>

      <span class="switch">
        <v-switch
          dark
          color="secondary"
          :label="`Dark Mode`"
          v-model="getLoggedUser.likesDark"
          @change="darkMode"
        ></v-switch>
      </span>
    </v-app-bar>

    <!--  DRAWER (OVO SA LEVE STR)-->
    <v-navigation-drawer v-model="drawerVisible" app disable-resize-watcher>
      <br />
      <v-list-item>
        <v-list-item-avatar>
          <img :src="`/rest/profilePic/${reRender}`"/>
          <!-- <img :src="`/images/users/${ this.getLoggedUser.image }`"/> -->
          <!-- <v-img src="https://avatars0.githubusercontent.com/u/28116193?s=460&v=4"></v-img> -->
        </v-list-item-avatar>

        <v-list-item-title
          v-model="user"
        >{{this.getLoggedUser.firstName + " " + this.getLoggedUser.lastName}}</v-list-item-title>
      </v-list-item>

      <v-divider class="blue-grey darken-1"></v-divider>
      <br />
      <br />

      <div v-if="isSuper">
        <v-list dense>
          <v-list-item @click="toggleDrawer" v-for="link in links_super" :key="link.text" router :to="link.route">
            <v-list-item-icon>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ link.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </div>

      <!-- Ako je ulogovan admin -->
      <div v-else-if="isAdmin">
        <v-list dense>
          <v-list-item @click="toggleDrawer" v-for="link in links_admin" :key="link.text" router :to="link.route">
            <v-list-item-icon>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ link.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </div>

      <!-- Ako je ulogovan obican user -->
      <div v-else-if="isLogged">
        <v-list dense>
          <v-list-item
            @click="toggleDrawer"
            v-for="link in links_user"
            :key="link.text"
            router
            :to="link.route"
          >
            <v-list-item-icon>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ link.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </div>

      <template v-slot:append>
        <div class="logout">
          <v-btn @click="logout" block class="blue-grey darken-1 white--text">Logout</v-btn>
        </div>
      </template>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  components: {},
  mounted() {
    this.$vuetify.theme.dark = this.getLoggedUser.likesDark;
  },

  data() {
    return {
      dark: false,
      drawerDisabled: true,
      drawerVisible: false,
      user: {
        firstName: "meeyat",
        lastName: "meel"
      },

      // linkovi za prelaz na druge delove str
      links_admin: [
        { icon: "mdi-view-dashboard", text: "Dashboard", route: "/admin" },
        { icon: "mdi-account", text: "Account", route: "/account" },
        { icon: "mdi-steam", text: "Organization", route: "/organization" },
        { icon: "mdi-credit-card", text: "Billing", route: "/billing" }
      ],

      links_super: [
        { icon: "mdi-view-dashboard", text: "Dashboard", route: "/admin" },
        { icon: "mdi-account", text: "Account", route: "/account" },
      ],

      links_user: [
        { icon: "mdi-view-dashboard", text: "Dashboard", route: "/dashboard" },
        { icon: "mdi-account", text: "Account", route: "/account" }
      ]
    };
  },

  methods: {
    ...mapActions({
      logoutAction: "users/logout",
      updateAcccount : "users/updateAccount",
    }),

    /** Otvara drawer ako postoji ulogovani korisnik */
    toggleDrawer: function() {
      this.drawerVisible = !this.drawerVisible;
    },

    logout: function() {
      //stara vrednost

      this.logoutAction()
        .then(() => {
          this.$vuetify.theme.dark = false;
          this.drawerVisible = false;
          this.drawerDisabled = true;
          this.$router.push("/login");
        })
        .catch(error => alert(error));
    },

    /** Kada korisnik pritisne na Home Dugme */
    homeButon: function() {
      if (this.isUser) {
        this.$router.push("/dashboard");
      } else if (this.isAdmin || this.isSuper) {
        this.$router.push("/admin");
      } else {
        this.$router.push("/");
      }
    },

    darkMode: function() {
      
      // this.getLoggedUser.likesDark = !this.getLoggedUser.likesDark;
      if (this.isLogged)
        this.updateAcccount(this.getLoggedUser);

      if (this.getLoggedUser.likesDark) {
        this.$vuetify.theme.dark = true;
      } else {
        this.$vuetify.theme.dark = false;
      }
    }
  },

  computed: {
    ...mapGetters({
      isLogged: "users/isLogged",
      getLoggedUser: "users/getUser",
      isUser: "users/isUser",
      isAdmin: "users/isAdmin",
      isSuper: "users/isSuper"
    }),
    /** Ako ne postoji ulogavan korisnik disable-uje dugme  */
    isDisabled() {
      return !this.isLogged;
    },

    reRender(){
      return this.getLoggedUser.firstName;
    }

  },
};
</script>

<style  scoped>
.naslov {
  margin-left: 20px;
  color: azure;
  font-size: 35px;
}

.d {
  background-color: red;
}

.transbtn {
  color: red;
}

.logout {
  margin: 15px;
  margin-bottom: 25px;
}

a {
  text-decoration: none;
}

.switch {
  display: flex;
  justify-content: center;
  margin-top: 25px;
  color: white !important;
}
</style>