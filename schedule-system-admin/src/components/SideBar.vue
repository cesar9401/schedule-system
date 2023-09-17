<script setup lang="ts">

type item = { name: string, icon: string, path: string };

const items: item[] = [
  {
    name: 'Salones',
    icon: 'meeting_room',
    path: '/classroom'
  },
  {
    name: 'Cursos',
    icon: 'subject',
    path: '/subject'
  },
  {
    name: 'Docentes',
    icon: 'groups',
    path: '/professor'
  }
]

</script>
<template>
  <div class="side-bar py-4 px-3">
    <ul class="p-0">
      <li v-for="it of items" class="d-flex align-items-center gap-1 rounded-poll mb-2">
        <router-link :to="it.path" class="d-flex align-items-center gap-1 py-3 px-3 rounded-poll mb-2 w-100 h-100">
          <span class="material-symbols-outlined">{{ it.icon }}</span>
          <p class="mb-0">{{ it.name }}</p>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<style lang="scss">
.side-bar {
  position: absolute;
  background-color: white;
  height: 100%;
  left: 0;
  top: 0;
  transition: .5s;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  overflow: hidden;
  width: 5rem;
  z-index: 100;

  &:hover {
    width: 15rem;
    overflow-y: auto;

    ul {
      li {
        justify-content: start;

        p {
          display: block;
        }
      }
    }
  }

  .active {
    background-color: red;

    span {
      color: blue;
    }

    p {
      color: black;
    }
  }

  ul li p {
    display: none;
  }

  ul {
    li {
      justify-content: center;
      color: #797979;
      font-weight: 600;
      transition: .1s;

      &:hover {
        background-color: #82E0AA;
        cursor: pointer;

        span {
          color: #1E8449;
        }

        p {
          color: #17202A;
        }
      }

      p {
        display: none;
        white-space: nowrap;
      }
    }
  }
}
</style>
