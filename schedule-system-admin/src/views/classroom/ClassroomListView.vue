<script setup lang="ts">
import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import type { Classroom } from '@/model/schedule.model';
import router from '@/router';
import ClassroomService from '@/services/ClassroomService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';

const classrooms: UnwrapNestedRefs<{ data: Classroom[] }> = reactive({ data: [] })

async function findAll() {
  const classrooms1 = await ClassroomService.findAll();
  classrooms.data = classrooms1.data ?? [];
}

function toEdit(id: number) {
  router.push(`/classroom/${id}`);
}

onMounted(() => {
  findAll();
});

</script>
<template>

  <div class="container">

    <Header to="/classroom/add" :type-header="HeaderEnum.HEADER_LIST_VIEW"></Header>

    <div class="my-5">
      <h1 class="text-success text-center">Salones</h1>
    </div>
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Nombre</th>
            <th scope="col">Capacidad</th>
            <th class="th-action" scope="col">Acciones</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr v-for="clzz of classrooms.data">
            <td>{{ clzz.name }}</td>
            <td>{{ clzz.recommendedCapacity }}</td>
            <td>
              <div class="d-inline-flex justify-content-end align-items-center">
                <button class="btn btn-sm btn-outline-success" @click="toEdit(clzz.classroomId)">
                  <span class="material-symbols-outlined">edit</span>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
