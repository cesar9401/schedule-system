<script setup lang="ts">

const props = defineProps({
  to: String,
  title: String
});

</script>
<template>
  <div class="mt-4" v-if="props">
    <router-link :to='to' class="text-decoration-none d-flex justify-content-start align-items-center gap-2">
      <span class="material-symbols-outlined">arrow_back</span>
      <h1 class="m-0 p-0 h5">{{ title }}</h1>
    </router-link>
  </div>
</template>
