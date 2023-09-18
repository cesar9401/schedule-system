<script setup lang="ts">

import { HeaderEnum } from '@/model/HeaderEnum';

const props = defineProps({
  to: String,
  title: String,
  crudTo: String,
  typeHeader: HeaderEnum
});

</script>
<template>
  <div v-if="props" class="mt-4 d-flex align-items-top">
    <div v-if="typeHeader == HeaderEnum.HEADER_CRUD || typeHeader == HeaderEnum.HEADER_LIST_VIEW_AND_CRUD">
      <router-link :to='to' class="text-decoration-none d-flex justify-content-start align-items-center gap-2 ">
        <span class="material-symbols-outlined">arrow_back</span>
        <h1 class="m-0 p-0 h5">{{ title }}</h1>
      </router-link>
    </div>
    <div v-if="typeHeader == HeaderEnum.HEADER_LIST_VIEW || typeHeader == HeaderEnum.HEADER_LIST_VIEW_AND_CRUD" class="d-flex justify-content-end align-items-center ms-auto">
      <router-link :to="crudTo" class="btn btn-success d-flex justify-content-center align-items-center gap-1">
        <span class="material-symbols-outlined">add</span>
        Agregar
      </router-link>
    </div>
  </div>
</template>
