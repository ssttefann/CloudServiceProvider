<template>
  <div class="home">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-start justify-center>
          <v-flex xs12 sm12 md8>
            <v-row>
              <v-col class="centered">
                <div class="display-3">
                  <span>Welcome to </span>
                  <span class="orange--text">cloud</span>
                  <span class="blue--text">Trek</span>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="centered" align-center>
                <!-- <span class="title text--primary">Nemamo za leba, jedemo u menzi</span> -->
              </v-col>
            </v-row>
            <br />
            <br />

            <v-layout row class="centered">
                <v-flex>
                  <router-link to="/login">
                    <v-btn class="blue-grey darken-1 white--text ma-5 lr-9">Go to dashboard</v-btn>
                  </router-link>
                  <v-btn class="gray darken-1 blue--text ma-5 ml-12">Contact sales</v-btn>
                </v-flex>

            </v-layout>

            <v-spacer></v-spacer>

            <v-row>
              <v-flex xs12 sm12 md12 lg6>
                <br>
                <br>
                <v-card class="centered mx-auto" max-width="550" max-height="315" raised>
                    <iframe width="560" height="315" src="https://www.youtube.com/embed/cvb49-Csq1o" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>                
                    <p class="font-italic font-weight-light">We basically just hired these people for the video</p>
                </v-card>
              </v-flex>

              <v-flex xs12 sm12 md12 lg6>
                <br>
                <br>
                <v-card class="centered mx-auto" max-width="550" max-height="315" raised>
                  <!-- <img width="550" height="315" src="https://www.oddee.com/wp-content/uploads/2017/09/worst-working-condition-e1505307604786.png" alt=""> -->
                  <img width="550" height="315" src="cond.png" alt="">
                  <p class="font-italic font-weight-light">Our QA Engineers</p>
                </v-card >
              </v-flex>
            </v-row>

            <br><br><br>
            <v-row>
              <v-col class="centered" align-center>
                <div class="display-1">See what's possible with 
                  <span class="orange--text">cloud</span>
                  <span class="blue--text">Trek</span>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="centered" align-center>
                <div class="subtitle-1">Tap into our world-class 
                  infrastructure and robust set of solutions to view,
                  add, and delete items from a list in today's complex,
                  multi-cloud environment.
                </div>
                <br>
                <v-btn class="gray darken-1 blue--text">See all solutions</v-btn>
              </v-col>
            </v-row>

            <br><br>
            <v-row>
              <v-col class="centered" align-center>
              <div class="display-1"> Our customers</div> <br>
              <div class="subtitle-1"> 
                Over the years, we have been lucky to have 
                leading companies from multiple different industries
                use our services for tackling their their business and often legal challanges
              </div>
              </v-col>
            </v-row>
            <br><br>

            <!-- CUSTOMERS -->
            <v-layout row>
              <v-flex xs6 sm6 md6 lg4>
                <a href="https://jovanjica.com/en/" target="_blank">
                <v-card class="ma-5 pa-3" hover>
                  <v-card-text>
                    <div><img width="60" height="60" src="https://www.najboljeizsrbije.com/files/prijave/n268_jovanjica--ae248c4caf86.jpg" alt=""></div>
                    <br>
                    <div class="gray--text subtitle-2 font-italic font-weight-light">Retail</div><br>
                    <div class="text--primary">
                     Naša kompanija je u <span class="font-weight-bold">potpunosti</span> posvećena proizvodnji
                      distribuciji organske hrane i ostalih proizvoda 
                      iz ove oblasti. Želja nam je da vam na što 
                      jednostavniji način omogućimo da uživate u 
                      svim čarima organski proizvedene hrane, 
                      bilo da je nabavite od nas, bilo da je sami proizvedete
                    </div>
                  </v-card-text>
                </v-card>
                </a>
              </v-flex>

              <v-flex xs6 sm6 md6 lg4>
                <a href="https://www.buzzfeed.com/" target="_blank">
                <v-card class="ma-5 pa-3" hover>
                  <v-card-text>
                    <div><img width="60" height="60" src="https://images-eu.ssl-images-amazon.com/images/I/41n8Q9FEMAL.png" alt=""></div>
                    <br>
                    <div class="gray--text subtitle-2 font-italic font-weight-light">Digital Media</div><br>
                    <div class="text--primary">
                      BuzzFeed, Inc. is the biggest and the best American Internet media, 
                      news and entertainment company with a focus on
                      digital media; it is based in New York City.
                      BuzzFeed was founded in 2006 by Jonah 
                      Peretti and John S. Johnson III, to focus on 
                      tracking viral content.
                    </div>
                  </v-card-text>
                </v-card>
                </a>
              </v-flex>

              <v-flex xs6 sm6 md6 lg4 hover>
                <a href="http://www.koliba.rs/" target="_blank">
                <v-card class="ma-5 pa-3" hover>
                  <v-card-text>
                    <div><img width="60" height="60" src="http://www.koliba.rs/images/koliba.png" alt=""></div>
                    <br>
                    <div class="gray--text subtitle-2 font-italic font-weight-light">Cuisine</div><br>
                    <div class="text--primary">
                      Börek is a family of baked filled pastries 
                      made of a thin flaky dough such as phyllo or 
                      yufka, of Turkish origins and also found in 
                      the cuisines of the Balkans,
                      the South Caucasus, Levant, Mediterranean, and other countries in Eastern Europe and Western Asia
                    </div>
                  </v-card-text>
                </v-card>
                </a>
              </v-flex>

               <v-flex xs6 sm6 md6 lg4 hover>
                <a href="https://en.wikipedia.org/wiki/Stratton_Oakmont" target="_blank">
                <v-card class="ma-5 pa-3" hover>
                  <v-card-text>
                    <div><img width="60" height="60" src="https://i.pinimg.com/originals/6b/f4/b5/6bf4b503559e5a5c5d1dfa09e6bee060.jpg" alt=""></div>
                    <br>
                    <div class="gray--text subtitle-2 font-italic font-weight-light">Finance</div><br>
                    <div class="text--primary">
                      Stratton Oakmont, Inc. is a Long Island, New York,
                       "over-the-counter" brokerage house founded in 
                       1989 by Jordan Belfort and Danny Porush. 
                       It defrauded many shareholders 
                       leading to the arrest and 
                       incarceration of several executives, and the closing of the firm in 1996
                    </div>
                  </v-card-text>
                </v-card>
                </a>
              </v-flex>

              <v-flex xs6 sm6 md6 lg4 hover>
                <a href="http://www.herba-srbija.com/" target="_blank">
                <v-card class="ma-5 pa-3" hover>
                  <v-card-text>
                    <div><img width="60" height="60" src="https://cdn11.bigcommerce.com/s-yyr3tzu8q0/images/stencil/1280x1280/products/133/474/Herbalife_Logo__65478.1550160738.png?c=2?imbypass=on" alt=""></div>
                    <br>
                    <div class="gray--text subtitle-2 font-italic font-weight-light">Marketing</div><br>
                    <div class="text--primary">
                      Herbalife Nutrition is a global multi-level
                      marketing corporation that develops 
                      and sells people and other nutritional products including 
                      health-and-wellness supplements for 
                      weight management personal care,
                      general wellness, fittness and sports performance.
                    </div>
                  </v-card-text>
                </v-card>
                </a>
              </v-flex>              

               <v-flex xs6 sm6 md6 lg4 hover>
                 <br>
                 <br>
                <div class="title ma-5 pl-10 mt-12" >
                   <p>Tell us your challenge</p>
                   <p>We'll help you get solving.</p>
                  <a href="">Contact us</a>
                </div>
              </v-flex>                                                  

            </v-layout>
            <br><br>


          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "home",
  components: {},

  data() {

    return {
      url : 'https://www.youtube.com/watch?v=cvb49-Csq1o',

    }
  },

  created() {},

  computed: {
    ...mapGetters({
      isLogged: "users/isLogged",
      isAdmin: "users/isAdmin"
    })
  },

};

</script>


<style scoped>
  .naslov {
    font-size: 3em;
    text-align: center;
  }

  .podnaslov {
    font-size: 1em;
    font-weight: light;
    text-align: center;
  }

  .centered {
    text-align: center;
  }

  a {
    text-decoration: none;
  }

  .dugmici{
    text-align: center;
    padding-left: 150px;
    padding-right: 150px;
  }
</style>

