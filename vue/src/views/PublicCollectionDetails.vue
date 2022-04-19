<template>
  <div class="collectionHome">
    <h4><router-link class ="returnPublicCollections" v-bind:to="{name: 'public-collections'}">Return</router-link></h4>
    <h1 id="collectionName">{{collectionName.collectionName}}</h1>
    <div v-for="record in records" v-bind:key="record.recordId">
        <div class="background">
          <img src="../assets/Gramophone_Vinyl_LP_Record.png" />
        </div>
        <div class="albumArt">
            <img  v-bind:src="record.coverImg"/>
            <h2>{{record.title}}</h2>
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
        this.collectionName = this.$store.state.collection.find(element => {
           return element.collectionId === this.$route.params.id
         });

    }

}
</script>

<style>

.collectionHome {
 position: relative;
  display: flex;
  flex-direction: column;
  color: white;
  margin: 1vw;
  padding: 20px;
  width: 500px;
  height: 500px;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 50%;
  margin: 5px;
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
  font-size: 10px;
  
  cursor: pointer;
}
.returnPublicCollections {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.albumArt {
    z-index: 0;
    
}
.albumArt img {
    width: 250px;
    height: auto;
}
.background{
    position:absolute;
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    align-items: center;
    justify-content: center;

}

.background img{
 
  display: flex;
    flex-direction: column;
    border-radius: 50%;
    align-items: center;
    justify-content: center;
    width: 500px;
    height: 500px;
    background-image: url("../assets/Gramophone_Vinyl_LP_Record.png");
    background-size: 600px;
    background-position: center;
    color: white;
    z-index: 0;
}

#collectionName {
  color: honeydew;
  background-color: black;
  text-align: center;
  border-radius: 10px;
  font-size: 40px;
  padding: 4px;
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
}
</style>