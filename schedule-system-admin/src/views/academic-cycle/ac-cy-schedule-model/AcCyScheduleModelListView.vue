<script setup lang="ts">

import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import router from '@/router';
import { onMounted, ref } from 'vue';

const academicCycleId = ref(1);

onMounted(() => {
  const acCyId = router.currentRoute.value.params['academicCycleId'];
  academicCycleId.value = Number(acCyId);
});
</script>

<template>
  <div class="container">
    <Header :type-header="HeaderEnum.HEADER_LIST_VIEW_AND_CRUD" :crud-to="'/academic-cycle/' + academicCycleId + '/model/add'" to="/academic-cycle" title="Regresar a ciclos academicos" />
    <div class="my-5">
      <h1 class="text-success text-center">Simulaciones</h1>
    </div>
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Responsable</th>
            <th scope="col">Descripci&oacute;n</th>
            <th scope="col">Fecha de creaci&oacute;n</th>
            <th scope="col" class="th-action">Acciones</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
        </tbody>
      </table>
    </div>
  </div>
</template>
