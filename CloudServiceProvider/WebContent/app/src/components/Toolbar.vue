
<template>
    <nav>
    <v-app-bar app class="blue-grey darken-1">

        <v-btn @click="toggleDrawer" target="_blank" text large>
            <v-icon >mdi-view-headline</v-icon>
        </v-btn>

        <v-btn href="/" large text>
        <div class="d-flex align-center">
            <v-img
            alt="Vuetify Logo"
            class="shrink mr-2"
            contain
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
            transition="scale-transition"
            width="40"
            />

            <label class="naslov">BDSM Services</label>

        </div>
        </v-btn>

        <v-spacer></v-spacer>

         <!-- DUGMICI NA TOOLBARU -->
        <v-btn href="" target="_blank" text>
            <v-icon size="20">mdi-help-circle</v-icon>
        </v-btn>

        <v-btn href="" target="_blank" text>
            <v-icon size="20">mdi-bell</v-icon>
        </v-btn>

    </v-app-bar> 

    
    <!--  DRAWER (OVO SA LEVE STR)-->
    
    <v-navigation-drawer v-model="drawer" app>
        <br>
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="https://avatars0.githubusercontent.com/u/28116193?s=460&v=4"></v-img>
          </v-list-item-avatar>
  
          <v-list-item-title>Meeyat</v-list-item-title>

        </v-list-item>
  
        <v-divider class="blue-grey darken-1" ></v-divider>
        <br><br>

        <v-list dense>
          <v-list-item v-for="link in links" :key="link.text" router :to="link.route">
            <v-list-item-icon>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-icon>
  
            <v-list-item-content>
              <v-list-item-title>{{ link.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <template v-slot:append>
          <div class="logout">
            <v-btn block class="blue-grey darken-1 white--text">Logout</v-btn>
          </div>
        </template>

      </v-navigation-drawer>

    </nav>

</template>

<script>


export default {

    components: {
        
    },
    data() {
        return{
            drawer: false,

            // linkovi za prelaz na druge delove str
            links : [
                {icon: 'mdi-view-dashboard', text: 'Dashboard', route: '/'},
                {icon: 'mdi-folder', text: 'About', route: '/register'},
                {icon: 'mdi-account-minus', text: 'Team', route: '/login'}
            ],
        }
    },

    methods : {
        // menja stanje drawera na svaki klik
        toggleDrawer : function() {
            this.drawer = !this.drawer;
        }
    }
}
</script>

<style  scoped>

    .naslov {
        margin-left : 20px;
        color: azure;
        font-size: 35px;
    }

    .d {
        background-color: red;
    }

    .transbtn{
        color: red;
    }

    .logout{
        margin: 15px;
        margin-bottom: 25px;
    }
</style>