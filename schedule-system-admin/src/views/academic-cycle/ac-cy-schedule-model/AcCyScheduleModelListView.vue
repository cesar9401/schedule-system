<script setup lang="ts">

import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import type { AcCyScheduleModel } from '@/model/schedule.model';
import router from '@/router';
import AcCyScheduleModelService from '@/services/AcCyScheduleModelService';
import { onMounted, reactive, ref, UnwrapNestedRefs } from 'vue';

const academicCycleId = ref(1);
const acCyScheduleModel: UnwrapNestedRefs<{ data: AcCyScheduleModel[] }> = reactive({ data: [] });

async function findAllByAcCy() {
  try {
    const response = await AcCyScheduleModelService.findAllByAcCy(academicCycleId.value);
    acCyScheduleModel.data = [...response.data];
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong!');
    await router.push({ name: 'all-academic-cycles' });
  }
}

onMounted(() => {
  const acCyId = router.currentRoute.value.params['academicCycleId'];
  academicCycleId.value = Number(acCyId);
  findAllByAcCy();
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
          <tr v-for="schedMod of acCyScheduleModel.data">
            <td>{{ schedMod.responsibleUser }}</td>
            <td>{{ schedMod.description }}</td>
            <td>{{ schedMod.entryDate }}</td>
            <td>
              <div class="d-inline-flex justify-content-end align-items-center gap-1">
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
