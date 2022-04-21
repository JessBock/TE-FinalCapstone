<template>
  <div class='recordDetails'>
    <div class="recordImage">
        <img v-bind:src="details.coverImg" />
        <h1 v-for="artist in details.artists" v-bind:key="artist.id">
          {{ artist.name }}
        </h1>
        <h2>{{details.title}}</h2>
    </div>
    <div class="text">
        <div class="collection-form">
            <label for="collection">Collection:</label>
            <select id="collection"  v-model.number='collectionId' >
                <option v-for="collection in $store.state.collections"  v-bind:value="collection.collectionId" v-bind:key="collection.collectionId">{{collection.collectionName}}</option>
            </select>
            <button type=submit v-on:click.prevent="saveToCollection()">Add to Collection</button>
        </div>
        <hr/>

        <div class="tracks">
            <h2>Tracks:</h2>
            <div v-for="track in details.tracklist" v-bind:key="track.position">
            {{track.title}} : {{track.duration}} - {{track.position}}
            </div>
        </div> 
        <hr/>
            <form class="comment-form" v-on:submit.prevent="setCommentsAndCondition">
                <div>
                <label for="condition">Record Condition:</label>
                <select id="condition" v-model="details.condition" :disabled="isSaved">
                    <option value="Mint">Mint</option>
                    <option value="Near Mint">Near Mint</option>
                    <option value="Good">Good</option>
                    <option value="Fair">Fair</option>
                    <option value="Poor">Poor</option>
                </select>
                </div>
                <div>
                <label for="comments">Comments:</label>
                <textarea type="text" id="comments" v-model="details.comments" :disabled="isSaved"></textarea>
                </div>
                <button v-on:click="toggleIsSaved" >{{isSaved? 'Update' : 'Save Changes'}}</button>
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
            isSaved: true
        }
    },
    created() {
        this.details = this.$store.state.library.find( record => {
            return record.recordId === this.$route.params.id;
        }

        )
    },
    methods: {
        toggleIsSaved() {
            this.isSaved = !this.isSaved;
        },
        saveToCollection() {
            collectionService.addRecordToCollection(this.details.recordId, this.collectionId);
            this.$confirm("You want to add '" +
            this.details.title + 
            "' to your collection?",
            "Add Record",
            "question").then(() => {
                collectionService.addRecordToCollection(this.details.recordId, this.collectionId);
                this.$router.push({name: "home"})
            });
           
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
hr {
    margin: 5px;
    width: 45vw;
}
.text {
    display: flex;
    flex-direction: column;
    background-color: black;
    color: honeydew;
    margin: 1vw;
    flex-grow: 2;
    padding: 20px;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 20px;
}

.text div {
    flex-grow: 1;
    display: flex;
    align-items: center;
    justify-items: center;
}

.tracks {
    flex-direction: column;
}

.comment-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin: 5px;
}

select {
    margin: 5px;
}

textarea {
    margin: 5px;
}

.recordDetails{
    display: flex;
    align-items: center;
    justify-content: center;
    margin: auto;
    flex-grow: 1;
    height: 85vh;
    
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
    color: honeydew;
    z-index: 0;
}
.recordDetails img {
    width: 250px;
    height: auto;

}

</style>