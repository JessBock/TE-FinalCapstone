<template>
  <div class='recordDetails'>
      <div class="recordImage">
      <img v-bind:src="details.coverImg" />
      <h1>{{details.artists[0].name}}</h1>
      <h2>{{details.title}}</h2>
      </div>
      <div class="text">
      
      <div v-for="track in details.tracklist" v-bind:key="track.position">
          {{track.title}} : {{track.duration}} - {{track.position}}
      </div>
      <select v-model.number='collectionId' >
          <option v-for="collection in $store.state.collection"  v-bind:value="collection.collectionId" v-bind:key="collection.collectionId">{{collection.collectionName}}</option>
      </select>
      <button type=submit v-on:click.prevent="saveToCollection()">Add to Collection</button>


      <form v-on:submit.prevent="setCommentsAndCondition">
            <label for="condition">Record Condition:</label>
            <select id="condition" v-model="details.condition">
                <option value="Mint">Mint</option>
                <option value="Near Mint">Near Mint</option>
                <option value="Good">Good</option>
                <option value="Fair">Fair</option>
                <option value="Poor">Poor</option>
            </select>
            <label for="comments">Comments:</label>
            <textarea type="text" id="comments" v-model="details.comments"></textarea>
            <button>Save Changes</button>
      </form>

      </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js';
import recordService from "@/services/RecordService.js";

export default {
    data() {
        return {
            details: {},
            collectionId: '',
        }
    },
    created() {
        this.details = this.$store.state.library.find( record => {
            return record.recordId === this.$route.params.id;
        }

        )
    },
    methods: {
        saveToCollection() {
            collectionService.addRecordToCollection(this.details.recordId, this.collectionId);
            
            
            
        },

        setCommentsAndCondition() {
            recordService.setCommentsAndCondition(this.details);
        }
       
    },
    computed: {
        
    }
}
</script>

<style>
.text {
    background-color: black;
    color: white;
    margin: 1vw;
    padding: 20px;
    width: 30vw;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 20px;
}
.recordDetails{
    display: flex;
    margin: auto;
    
}

.recordImage{
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
.recordDetails img {
    width: 250px;
    height: auto;

}

</style>