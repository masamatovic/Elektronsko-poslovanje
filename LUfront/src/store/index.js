import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    processID : ""
  },
  mutations: {
      addProcessID(state, procesID) {
      // mutate state
      state.processID = procesID;
    },
  },
  actions: {
  },
  modules: {
  }
})
