<script setup lang="ts">

import { HeaderEnum } from '@/model/HeaderEnum';

const props = defineProps({
  to: String,
  title: String,
  typeHeader: HeaderEnum
});

</script>
<template>
  <div class="mt-4" v-if="props">
    <div v-if="typeHeader == HeaderEnum.HEADER_CRUD">
      <router-link :to='to' class="text-decoration-none d-flex justify-content-start align-items-center gap-2">
        <span class="material-symbols-outlined">arrow_back</span>
        <h1 class="m-0 p-0 h5">{{ title }}</h1>
      </router-link>
    </div>
    <div v-else-if="typeHeader == HeaderEnum.HEADER_LIST_VIEW" class="mt-4 d-flex justify-content-end align-items-center">
      <router-link :to="to" class="btn btn-success d-flex justify-content-center align-items-center gap-1 col-2">
        <span class="material-symbols-outlined">add</span>
        {{ title || 'Agregar' }}
      </router-link>
    </div>
    <div v-else>Error!</div>
  </div>
</template>
