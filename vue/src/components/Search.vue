<template>
  <form v-on:submit.prevent="searchWebsite()">
    <input class="searchButton" type="text" v-model="searchTerm" style="padding: 8px;" />
    <button>Search</button>
  </form>
</template>

<script>
import recordService from "@/services/RecordService.js";

export default {
  name: "search",

  data() {
    return {
      searchTerm: "",
      errorMsg: "",
    };
  },

  methods: {
    searchWebsite() {
      const self = this;
      recordService
        .search(this.searchTerm)
        .then((response) => {
          this.$store.commit(
            "UPDATE_RESULTS",
            self.filterResults(response.data.results)
          );
          this.searchTerm = "";
          this.$store.commit("UPDATE_RECORD_DETAILS", []);
          this.$router.push({ name: "results" });
        })
        .catch((error) => {
          if (error.response) {
            this.errorMsg =
              "Received an error from the server: " + error.response.statusText;
          } else if (error.request) {
            this.errorMsg = "Error submitting request, could not reach server";
          } else {
            this.errorMsg =
              "I'm sorry, there was an error. Please try again later.";
          }
        });
    },
    filterResults(results) {
      return results.filter((img) => {
        return img.type == "master";
      });
    },
  },
  computed: {},
  created() {},
};
</script>

<style scope>

</style>