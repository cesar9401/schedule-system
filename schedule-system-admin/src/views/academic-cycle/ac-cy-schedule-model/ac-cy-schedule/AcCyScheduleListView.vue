<script setup lang="ts">
import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import type { AcCyScheduleDto } from '@/model/schedule.model';
import router from '@/router';
import AcCyScheduleService from '@/services/AcCyScheduleService';
import { onMounted, reactive, ref, UnwrapNestedRefs } from 'vue';

const academicCycleId = ref(1);
const acCyScheduleModelId = ref(1);
const acCySchedules: UnwrapNestedRefs<{ data: AcCyScheduleDto[] }> = reactive({ data: [] });

async function findSchedules () {
  const response = await AcCyScheduleService.findByAcCyScheduleModelId(acCyScheduleModelId.value);
  acCySchedules.data = [...response.data];
}

async function downloadSchedule(acCyScheduleId: number) {
  const response = await AcCyScheduleService.exportByAcCyScheduleId(acCyScheduleId);
  const url = window.URL.createObjectURL(new Blob([response.data]));
  const link = document.createElement('a');
  link.href = url;
  link.setAttribute('download', 'schedule.xlsx');
  document.body.appendChild(link);
  link.click();
}

onMounted(() => {
  const acCyId = router.currentRoute.value.params['academicCycleId'];
  const acCySchedModelId = router.currentRoute.value.params['acCyScheduleModelId'];
  if (!acCyId || !acCySchedModelId) return;

  academicCycleId.value = Number(acCyId);
  acCyScheduleModelId.value = Number(acCySchedModelId);
  findSchedules();
});

</script>

<template>
  <div class="container">
    <Header :type-header="HeaderEnum.HEADER_CRUD" :to="'/academic-cycle/' + academicCycleId + '/model'" title="Regresar a simulaciones" />
    <div class="my-5">
      <h1 class="text-success text-center">Horarios</h1>
    </div>
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Periodos</th>
            <th scope="col">Número de periodos cubiertos</th>
            <th scope="col" class="th-action">Acciones</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr v-for="schedule of acCySchedules.data">
            <td>{{ schedule.totalNumberOfPeriods }}</td>
            <td>{{ schedule.totalCoveredPeriods }}</td>
            <td>
              <div class="d-inline-flex justify-content-end align-items-center gap-1">
                <button @click="downloadSchedule(schedule.acCyScheduleId)" type="button" class="btn btn-sm btn-outline-success">
                  <span class="material-symbols-outlined">download</span>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
