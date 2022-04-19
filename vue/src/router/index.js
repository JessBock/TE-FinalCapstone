import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Results from '@/views/Results.vue'
import Collections from '@/views/Collections.vue'
import CollectionDetails from '@/views/CollectionDetails.vue'
import LibraryRecordDetails from '@/views/LibraryRecordDetails.vue'
import PublicCollections from '@/views/PublicCollections.vue'
import PublicCollectionDetails from '@/views/PublicCollectionDetails.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/results",
      name: "results",
      component: Results,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/collections",
      name: "view-collections",
      component: Collections,
      meta: {
        requiresAuth: false
      }

    },
    {
      path: '/collections/:id',
      name: 'collection-details',
      component: CollectionDetails,
      meta: {
        requiresAuth: false
      }
    },

  {
      path: '/library/:id',
      name: 'library-record-details',
      component: LibraryRecordDetails,
      meta: {
        requiresAuth: false
      }
  },
  {
      path: '/public/collections',
      name: 'public-collections',
      component: PublicCollections,
      meta: {
        requiresAuth: false
      }
  },
  {
      path: '/public/collections/:id',
      name: 'public-collection-details',
      component: PublicCollectionDetails,
      meta: {
        requiresAuth: false
      }
  }

  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
