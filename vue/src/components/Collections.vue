<template>
  <div class="collectionPage">
    <div class="addACollection">

        <input type="text" v-model="collectionName" placeholder="Enter A Collection Name" />
        <div class='makeInline' >
        <input 
            type="checkbox" 
            id="isPublic" 
            name="isPublic"
            v-model="isPublic"     
          />
        <label id='publicCheck' for="isPublic" > Make Collection Public? </label>
        </div>
        <button id="addToCollection" type="submit" v-on:click.prevent="createCollection(collectionName, isPublic)" >Create A New Collection</button>
      
      </div>
    <div class="allCollection">

        <div class="eachCollection" v-for="collection in displayCollections" v-bind:key="collection.collectionId">
          <img src="../assets/Gramophone_Vinyl_LP_Record.png" />
      
          <h1><router-link class="collectionLink" v-bind:to="{name: 'collection-details', params: {id: collection.collectionId}}">{{collection.collectionName}}</router-link></h1>
          <button class = "deleteBtn" v-on:click="deleteCollection(collection)">Delete Collection</button>
        </div>
    </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js';
export default {
  name: 'collections',
  data() {
    return {
      collections: [],
      collectionName: '',
      isPublic: false
            
            
            
        }
    },
  computed: {
    displayCollections() {
      let collections = this.$store.state.collections;
      return collections;
    }
  },
  created() {
    const self = this;
    collectionService.getCollections()
    .then( response => {
      self.collections = response.data;
      return this.$store.commit('SET_COLLECTION', response.data);
    })
  },
methods: {
  createCollection(collectionName, isPublic) {
    this.collectionName = '';
    this.isPublic = false;
     /* this.$store.commit("SAVE_TO_COLLECTION", collectionName, isPublic); */
    collectionService.addCollection(collectionName, isPublic)
    .then(() => {
        collectionService.getCollections()
        .then( response =>
          {return this.$store.commit('SET_COLLECTION', response.data)})
    })  
  },

  deleteCollection(collection) {
    this.$confirm(
      'Are you sure you want to delete "' +
      collection.collectionName +
      '"',
      "REMOVE COLLECTION",
      "warning")
    .then (() => {
      collectionService.deleteCollection(collection.collectionId),
      this.$store.commit('DELETE_FROM_COLLECTION', collection.collectionId)
    })

    },

    deleteCollectionFromRecord(collectionId, recordId){
      collectionService.deleteRecordFromCollection(collectionId, recordId)
       .then(
        collectionService.getCollections()
        .then( response => {
          this.collections = response.data;
          },
          location.reload()
        )
      )
    },
  
  }
}


</script>

<style scoped>

.collectionLinks {
animation: fadeOutAnimation ease 15s;
    animation-iteration-count: 1;
    animation-fill-mode: forwards;

}

.allCollection {
  flex-wrap:wrap;
  display: flex;
  justify-content: center;
  font-family: 'limelight-regular', 'serif';
}

.eachCollection {
  background-color: rgba(0,0,0,.8);
  border-radius: 15px;
  max-width: 25vw;
  margin: 10px;
  text-align: center;
  padding: 5px;
  display: flex;
  flex-direction: column;

}

.collectionLink {
  color: honeydew;
  text-decoration: none;
  
}

.collectionLink:hover {
  color: #CC5500;
  text-decoration: underline;
}

.addACollection{
  display: flex;
  flex-direction: column;
  background-color: rgba(0,0,0,.8);
  color: honeydew;
  max-width: 25vw;
  margin: auto;
  padding: 10px;
  border-radius: 5px;
  margin-top: 5px;
}

.collectionPage {
  overflow-x: hidden;
}

input  {
  margin: 5px;
}

#publicCheck {
  font-size: 15px;
}

.makeInline {
  display: block;
}

@keyframes fadeOutAnimation {
    0% {
        opacity: 0;
    }
    100% {
        opacity: 1;
     }
}

</style>