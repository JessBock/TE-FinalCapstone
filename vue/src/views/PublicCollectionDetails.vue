<template>
  <div class="collectionHome">
    <h4><router-link class ="returnPublicCollections" v-bind:to="{name: 'public-collections'}">Return</router-link></h4>
    <h2 id="collectionName">{{collectionName.collectionName}}</h2>
    <div class="recordsInCollection">
    <div class="record" v-for="record in records" v-bind:key="record.recordId">
            <div class="background">
          <img src="../assets/Gramophone_Vinyl_LP_Record.png" />
          </div>
        <div class="albumArt">
            <img  v-bind:src="record.coverImg" id="singleArt"/>
            <h2>{{record.title}}</h2>
        </div>
      </div>
    </div> 
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js'
export default {
    data() {
        return {
            records: [],
            collectionName: ''

        }
    },

    created(){
        collectionService.getCollectionById(this.$route.params.id)
        .then( response => {
            this.records = response.data;
        });
        this.collectionName = this.$store.state.publicCollection.find(element => {
           return element.collectionId === this.$route.params.id
         });

    }

}
</script>

<style>

.returnPublicCollections {
  color: honeydew;
  text-decoration: none;
  background-color: rgba(0,0,0,.8);
  border-radius: 5px;
}

.returnPublicCollections:hover {
  color: #CC5500;
  text-decoration: underline;
}

#collectionName {
  color: honeydew;
  background-color: rgba(0,0,0,.8);
  border-radius: 10px;
  font-size: 40px;
  padding: 4px;
  font-family: "limelight-regular", serif;
}

.collectionHome {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.recordsInCollection {
  display: flex;
  flex-wrap: wrap;
}

.record {
 position: relative;
  display: flex;
  flex-direction: column;
  color: honeydew;
  margin: 1vw;
  padding: 20px;
  width: 500px;
  height: 500px;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 50%;
  margin: 5px;
  font-family: "limelight-regular", serif;
  font-size: 20px;
}

.albumArt {
  z-index: 1;   
}

.background {
  position:absolute;
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  align-items: center;
  justify-content: center;
}

.background img{
 position:absolute;
 background-position: center;
 height: 100%;
 width: 100%;
 z-index: 0;
}

#singleArt {
  width: 250px;
  height: auto;
  border-radius: 0 !important;
}

.background:hover {
  animation: rotation 8s infinite linear;
}

@keyframes rotation {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(359deg);
  }
}
</style>